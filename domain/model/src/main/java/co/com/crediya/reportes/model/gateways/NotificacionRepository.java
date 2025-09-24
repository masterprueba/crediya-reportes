package co.com.crediya.reportes.model.gateways;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import reactor.core.publisher.Mono;

public interface NotificacionRepository {
    Mono<Void> enviarCorreoAprobados(CantidadPrestamosAprobados cantidad);
}
