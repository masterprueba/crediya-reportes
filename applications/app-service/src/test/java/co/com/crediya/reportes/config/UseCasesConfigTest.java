package co.com.crediya.reportes.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ComponentScan(basePackages = {"co.com.crediya.reportes.usecase", "co.com.crediya.reportes.securityadapter"})
 class UseCasesConfigTest {

    @Autowired
    private ApplicationContext context;

        @Test
        void testUseCaseBeansExist() {
            assertNotNull(context.getBean("consultarTotalAprobadasUseCase"));
            assertNotNull(context.getBean("procesarSolicitudAprobadaUseCase"));
        }
}