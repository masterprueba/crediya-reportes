package co.com.crediya.reportes.usecase.procesarsolicitudaprobada;

import co.com.crediya.reportes.model.SolicitudAprobadasEvent;
import co.com.crediya.reportes.model.gateways.EventoProcesadoRepository;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;


@RequiredArgsConstructor
public class ProcesarSolicitudAprobadaUseCase {

    private final EventoProcesadoRepository repository;
    private final ReportesCountRepository reportesCountRepository;
    private static final Logger log = Loggers.getLogger(ProcesarSolicitudAprobadaUseCase.class);

    public Mono<Void> procesar(SolicitudAprobadasEvent event) {
        return repository.marcarSinoProceso(event)
                .flatMap(isDuplicado -> {
                    if (Boolean.TRUE.equals(isDuplicado)) {
                        log.info("El evento con ID {} ya fue procesado anteriormente. Se omite el procesamiento duplicado.", event.getEventId());
                        return Mono.empty();
                    } else {
                        log.info("Procesando evento con ID {} para la solicitud ID {}. fechaProcesada {}", event.getEventId(), event.getSolicitudId(), event.getFechaProcesada());
                        return reportesCountRepository.increment();
                    }
                });
    }
}
