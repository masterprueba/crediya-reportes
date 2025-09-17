package co.com.crediya.reportes.model.gateways;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import co.com.crediya.reportes.model.SolicitudAprobadasEvent;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ReportesCountRepository {
    Mono<CantidadPrestamosAprobados> snapshot();

    Mono<Void> increment(SolicitudAprobadasEvent cantidad);
}
