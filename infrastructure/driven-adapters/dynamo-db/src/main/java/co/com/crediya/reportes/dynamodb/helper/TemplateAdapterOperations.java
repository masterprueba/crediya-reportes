package co.com.crediya.reportes.dynamodb.helper;

import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;

public abstract class TemplateAdapterOperations<E, K, V> {
    private final Class<V> dataClass;
    private final Function<V, E> toEntityFn;
    protected ObjectMapper mapper;
    private final DynamoDbAsyncTable<V> table;

    @SuppressWarnings("unchecked")
    protected TemplateAdapterOperations(DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient,
                                        ObjectMapper mapper,
                                        Function<V, E> toEntityFn,
                                        String tableName) {
        this.toEntityFn = toEntityFn;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<V>) genericSuperclass.getActualTypeArguments()[2];
        table = dynamoDbEnhancedAsyncClient.table(tableName, TableSchema.fromBean(dataClass));
    }

    public Mono<E> save(E model) {
        return Mono.fromFuture(table.putItem(toEntity(model))).thenReturn(model);
    }

    public Mono<E> getById(K id) {
        return Mono.fromFuture(table.getItem(Key.builder()
                        .partitionValue(AttributeValue.builder().s((String) id).build())
                        .build()))
                .map(this::toModel);
    }

    public Mono<E> delete(E model) {
        return Mono.fromFuture(table.deleteItem(toEntity(model))).map(this::toModel);
    }

    public Mono<List<E>> query(QueryEnhancedRequest queryExpression) {
        PagePublisher<V> pagePublisher = table.query(queryExpression);
        return listOfModel(pagePublisher);
    }

    public Mono<List<E>> queryByIndex(QueryEnhancedRequest queryExpression, String... index) {
        // Si no se pasa índice, usa la tabla principal
        if (index.length == 0) {
            PagePublisher<V> pagePublisher = table.query(queryExpression);
            return listOfModel(pagePublisher);
        }
        // Si se pasa índice, usa el índice
        DynamoDbAsyncIndex<V> queryIndex = table.index(index[0]);
        SdkPublisher<Page<V>> pagePublisher = queryIndex.query(queryExpression);
        return listOfModel(pagePublisher);
    }

    private Mono<List<E>> listOfModel(PagePublisher<V> pagePublisher) {
        return Mono.from(pagePublisher).map(page -> page.items().stream().map(this::toModel).toList());
    }

    private Mono<List<E>> listOfModel(SdkPublisher<Page<V>> pagePublisher) {
        return Mono.from(pagePublisher).map(page -> page.items().stream().map(this::toModel).toList());
    }

    private V toEntity(E model) {
        return mapper.map(model, dataClass);
    }

    protected E toModel(V data) {
        return data != null ? toEntityFn.apply(data) : null;
    }
}