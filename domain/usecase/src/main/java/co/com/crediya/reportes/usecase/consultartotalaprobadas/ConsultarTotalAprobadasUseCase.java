package co.com.crediya.reportes.usecase.consultartotalaprobadas;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ConsultarTotalAprobadasUseCase {

    private final ReportesCountRepository canttidad;
    public Mono<CantidadPrestamosAprobados> consultarAprobadas() {
        return canttidad.snapshot();
    }
}
