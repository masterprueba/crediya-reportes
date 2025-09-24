package co.com.crediya.reportes.config;

import co.com.crediya.reportes.model.gateways.EventoProcesadoRepository;
import co.com.crediya.reportes.model.gateways.NotificacionRepository;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import co.com.crediya.reportes.usecase.consultartotalaprobadas.ConsultarTotalAprobadasUseCase;
import co.com.crediya.reportes.usecase.procesarsolicitudaprobada.ProcesarSolicitudAprobadaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.crediya.reportes.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {


        @Bean
        public ConsultarTotalAprobadasUseCase consultarTotalAprobadasUseCase(ReportesCountRepository port, NotificacionRepository notificacionRepository) {
                return new ConsultarTotalAprobadasUseCase(port, notificacionRepository);
        }

        @Bean
        public ProcesarSolicitudAprobadaUseCase procesarSolicitudAprobadaUseCase(EventoProcesadoRepository eventoProcesadoRepository, ReportesCountRepository port) {
                return new ProcesarSolicitudAprobadaUseCase(eventoProcesadoRepository, port);
        }
}
