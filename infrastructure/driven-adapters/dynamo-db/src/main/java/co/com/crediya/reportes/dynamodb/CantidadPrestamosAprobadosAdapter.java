package co.com.crediya.reportes.dynamodb;

import co.com.crediya.reportes.dynamodb.entity.PrestamosAprobadosEntity;
import co.com.crediya.reportes.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.time.Instant;
import java.util.List;


@Repository
public class CantidadPrestamosAprobadosAdapter extends TemplateAdapterOperations<CantidadPrestamosAprobados , String, PrestamosAprobadosEntity > implements ReportesCountRepository {

    private final Logger log = Loggers.getLogger(CantidadPrestamosAprobadosAdapter.class);

    private static final String PK_VALUE = "#APROBADAS";

    public CantidadPrestamosAprobadosAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, CantidadPrestamosAprobados.class), "prestamos_aprobados");
    }

    public Mono<List<CantidadPrestamosAprobados>> getEntityBySomeKeys(String partitionKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey);
        return query(queryExpression);
    }

    public Mono<List<CantidadPrestamosAprobados>> getEntityBySomeKeysByIndex(String partitionKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey);
        return queryByIndex(queryExpression);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .build();
    }

    @Override
    public Mono<CantidadPrestamosAprobados> snapshot() {
        log.info("Obteniendo snapshot de la cantidad de préstamos aprobados...");
        return getEntityBySomeKeysByIndex(PK_VALUE)
                .flatMap(list -> list.stream().findFirst()
                        .map(Mono::just)
                        .orElse(Mono.empty()));
    }

    @Override
    public Mono<Void> increment() {
        log.info("Incrementando la cantidad de préstamos aprobados...");
        return snapshot()
                .flatMap(existing -> {
                    existing.setId_aprobadas(PK_VALUE);
                    existing.setTotal(existing.getTotal() + 1);
                    existing.setFechaActualiza(Instant.now().toString());
                    return save(existing);
                })
                .then();
    }
}
