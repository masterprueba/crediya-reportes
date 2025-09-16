package co.com.crediya.reportes.usecase.consultartotalaprobadas;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

class ConsultarTotalAprobadasUseCaseTest {

    @Mock
    private ReportesCountRepository canttidad;

    @InjectMocks
    private ConsultarTotalAprobadasUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarAprobadas() {
        CantidadPrestamosAprobados cantidad = CantidadPrestamosAprobados.builder()
                .id_aprobadas("test-id")
                .total(10L)
                .fechaActualiza("2023-10-10T10:10:10")
                .build();
        when(canttidad.snapshot()).thenReturn(Mono.just(cantidad));

        useCase.consultarAprobadas()
                .as(StepVerifier::create)
                .expectNext(cantidad)
                .verifyComplete();
    }
}
