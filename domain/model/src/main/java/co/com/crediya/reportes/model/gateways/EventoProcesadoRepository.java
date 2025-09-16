package co.com.crediya.reportes.model.gateways;

import co.com.crediya.reportes.model.SolicitudAprobadasEvent;
import reactor.core.publisher.Mono;

public interface EventoProcesadoRepository {
    Mono<Boolean> marcarSinoProceso(SolicitudAprobadasEvent event);
}
