package co.com.crediya.reportes.config;

import co.com.crediya.reportes.usecase.consultartotalaprobadas.ConsultarTotalAprobadasUseCase;
import co.com.crediya.reportes.usecase.procesarsolicitudaprobada.ProcesarSolicitudAprobadaUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public ConsultarTotalAprobadasUseCase consultarTotalAprobadasUseCase() {
            return new ConsultarTotalAprobadasUseCase();
        }

        @Bean
        public ProcesarSolicitudAprobadaUseCase procesarSolicitudAprobadaUseCase() {
            return new ProcesarSolicitudAprobadaUseCase();
        }
    }

    static class ConsultarTotalAprobadasUseCase {
        public String consultarAprobadas() {
            return "MyUseCase Test";
        }
    }

    static class ProcesarSolicitudAprobadaUseCase {
        public String consultarAprobadas() {
            return "MyUseCase Test";
        }
    }
}