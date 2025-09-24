package co.com.crediya.reportes.usecase.consultartotalaprobadas;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import co.com.crediya.reportes.model.gateways.NotificacionRepository;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ConsultarTotalAprobadasUseCase {

    private final ReportesCountRepository canttidad;
    private final NotificacionRepository notificacion;

    public Mono<CantidadPrestamosAprobados> consultarAprobadas() {
        return canttidad.snapshot();
    }

    public Mono<Void> enviarCorreoDiarioAprobados() {
        return canttidad.snapshot().
                flatMap(notificacion::enviarCorreoAprobados);
    }
}
