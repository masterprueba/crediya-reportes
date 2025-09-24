package co.com.crediya.reportes.sqs.listener;

import co.com.crediya.reportes.usecase.consultartotalaprobadas.ConsultarTotalAprobadasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Component("sqsReporteDiarioProcessor")
@RequiredArgsConstructor
public class SQSReporteDiarioProcessor  implements Function<Message, Mono<Void>> {

    private static final Logger log = Loggers.getLogger(SQSReporteDiarioProcessor.class);
    private final ConsultarTotalAprobadasUseCase useCase;

    @Override
    public Mono<Void> apply(Message message) {
        return useCase.enviarCorreoDiarioAprobados()
                .doOnSuccess(result -> log.info(" Reporte diario generado exitosamente: " + result))
                .doOnError(e -> log.error(" Error generando reporte diario: " + e.getMessage()))
                .then();
    }


}
