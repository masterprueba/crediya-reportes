package co.com.crediya.reportes.model.gateways;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import reactor.core.publisher.Mono;

public interface ReportesCountRepository {
    Mono<CantidadPrestamosAprobados> snapshot();

    Mono<Void> increment();
}
