package co.com.crediya.reportes.dynamodb;

import co.com.crediya.reportes.dynamodb.entity.EventoProcesadoEntity;
import co.com.crediya.reportes.dynamodb.helper.TemplateAdapterOperations;
import co.com.crediya.reportes.model.SolicitudAprobadasEvent;
import co.com.crediya.reportes.model.exceptions.DomainException;
import co.com.crediya.reportes.model.gateways.EventoProcesadoRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;


@Repository
public class EventoProcesadoAdapter extends TemplateAdapterOperations<SolicitudAprobadasEvent, String, EventoProcesadoEntity> implements EventoProcesadoRepository {

    private final Logger log = Loggers.getLogger(EventoProcesadoAdapter.class);


    public EventoProcesadoAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper) {
        super(connectionFactory, mapper, d -> mapper.map(d, SolicitudAprobadasEvent.class), "eventos_procesados");
    }

    public Mono<List<SolicitudAprobadasEvent>> getEntityBySomeKeys(String partitionKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey);
        return query(queryExpression);
    }

    public Mono<List<SolicitudAprobadasEvent>> getEntityBySomeKeysByIndex(String partitionKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey);
        return queryByIndex(queryExpression);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .build();
    }

    @Override
    public Mono<Boolean> marcarSinoProceso(SolicitudAprobadasEvent event) {
        // 1 dia ttl
        event.setTtl(System.currentTimeMillis() / 1000 + 86400);
        return getById(event.getEventId())
                .flatMap(existingEvent -> {
                    log.info("El evento con ID {} ya fue procesado anteriormente. Se omite el procesamiento duplicado.", existingEvent.getEventId());

                    return Mono.just(true);
                })
                .switchIfEmpty(
                        save(event)
                                .doOnSuccess(savedEvent -> log.info("Evento con ID {} procesado y guardado exitosamente.", savedEvent.getEventId()))
                                .doOnError(e -> new DomainException("Error guardando el evento procesado: " + e.getMessage()))
                                .thenReturn(false)
                );
    }

}
