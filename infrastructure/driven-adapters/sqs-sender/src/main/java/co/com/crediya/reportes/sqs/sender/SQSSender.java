package co.com.crediya.reportes.sqs.sender;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import co.com.crediya.reportes.model.gateways.NotificacionRepository;
import co.com.crediya.reportes.sqs.sender.config.SQSSenderProperties;
import co.com.crediya.reportes.sqs.sender.helper.CorreoUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.List;

@Service
@Log4j2
public class SQSSender implements NotificacionRepository {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;

    public SQSSender(SQSSenderProperties properties, @Qualifier("configSqs") SqsAsyncClient client) {
        this.properties = properties;
        this.client = client;
    }

    public Mono<String> send(String message) {
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.queueUrl())
                .messageBody(message)
                .build();
    }

    @Override
    public Mono<Void> enviarCorreoAprobados(CantidadPrestamosAprobados cantidad) {
        if (properties.to().isEmpty()) {
            return Mono.error(new IllegalStateException("No recipients configured"));
        }
        
        String asunto = "Reporte Diario de Préstamos Aprobados";
        String cuerpo = CorreoUtils.construirCuerpoReporteDiario(cantidad);
        
        return Flux.fromIterable(properties.to())
            .flatMap(recipient -> {
                String mensaje = CorreoUtils.construirCorreo(asunto, cuerpo, recipient);
                return send(mensaje)
                    .doOnSuccess(messageId -> log.info("Notificación enviada a {}: {}", recipient, messageId))
                    .doOnError(error -> log.error("Error al enviar notificación a {}: {}", recipient, error.getMessage()));
            })
            .then();
    }
}
