package co.com.crediya.reportes.sqs.listener;

import co.com.crediya.reportes.sqs.listener.dto.SolicitudAprobadaRequest;
import co.com.crediya.reportes.sqs.listener.mapper.SolicitudAprobadasMapper;
import co.com.crediya.reportes.usecase.procesarsolicitudaprobada.ProcesarSolicitudAprobadaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private static final Logger log = Loggers.getLogger(SQSProcessor.class);
    private final ProcesarSolicitudAprobadaUseCase procesarSolicitudAprobadaUseCase;
    private final ObjectMapper objectMapper;
    private final SolicitudAprobadasMapper mapper;

    @Override
    public Mono<Void> apply(Message message) {
        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), SolicitudAprobadaRequest.class))
                .doOnSuccess(dto -> log.info(" JSON parseado correctamente - SolicitudId: " + dto.idSolicitud()))
                .flatMap(this::procesarSolicitudAprobada)
                .doOnSuccess(ignored -> log.info(" Mensaje procesado y confirmado exitosamente"))
                .doOnError(e -> log.info(" Error procesando mensaje SQS - MessageId: " + message.messageId() + ", Error: " + e.getMessage()))
                .then();
    }

    private Mono<Void> procesarSolicitudAprobada(SolicitudAprobadaRequest dto) {
        return procesarSolicitudAprobadaUseCase.procesar(mapper.toModel(dto))
                .doOnSuccess(ignored -> log.info(" Solicitud aprobada procesada exitosamente - SolicitudId: " + dto.idSolicitud()))
                .doOnError(e -> log.info(" Error procesando solicitud aprobada - SolicitudId: " + dto.idSolicitud() + ", Error: " + e.getMessage()))
                .then();
    }
}
