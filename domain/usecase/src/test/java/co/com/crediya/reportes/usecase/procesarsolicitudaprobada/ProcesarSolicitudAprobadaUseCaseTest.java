package co.com.crediya.reportes.usecase.procesarsolicitudaprobada;

import co.com.crediya.reportes.model.SolicitudAprobadasEvent;
import co.com.crediya.reportes.model.gateways.EventoProcesadoRepository;
import co.com.crediya.reportes.model.gateways.ReportesCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcesarSolicitudAprobadaUseCaseTest {

    @Mock
    private EventoProcesadoRepository eventoProcesadoRepository;

    @Mock
    private ReportesCountRepository reportesCountRepository;

    @InjectMocks
    private ProcesarSolicitudAprobadaUseCase useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void procesarEventoNuevo() {
        SolicitudAprobadasEvent event = new SolicitudAprobadasEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setSolicitudId("123");
        event.setFechaProcesada(new Date().toString());
        event.setMontoAprobado(new BigDecimal(1000));
        
        when(eventoProcesadoRepository.marcarSinoProceso(any(SolicitudAprobadasEvent.class))).thenReturn(Mono.just(false));
        when(reportesCountRepository.increment(event.getMontoAprobado())).thenReturn(Mono.empty());

        useCase.procesar(event)
                .as(StepVerifier::create)
                .verifyComplete();

        verify(reportesCountRepository).increment(event.getMontoAprobado());
    }

    @Test
    void procesarEventoDuplicado() {
        SolicitudAprobadasEvent event = new SolicitudAprobadasEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setSolicitudId("123");
        event.setFechaProcesada(new Date().toString());
        event.setMontoAprobado(new BigDecimal(1000));

        when(eventoProcesadoRepository.marcarSinoProceso(any(SolicitudAprobadasEvent.class))).thenReturn(Mono.just(true));

        useCase.procesar(event)
                .as(StepVerifier::create)
                .verifyComplete();

        verify(reportesCountRepository, never()).increment(event.getMontoAprobado());
    }

    @Test
    void procesarConErrorEnRepositorio() {
        SolicitudAprobadasEvent event = new SolicitudAprobadasEvent();
        event.setEventId(UUID.randomUUID().toString());
        event.setSolicitudId("123");
        event.setFechaProcesada(new Date().toString());
        event.setMontoAprobado(new BigDecimal(1000));
        
        RuntimeException exception = new RuntimeException("Error en base de datos");
        when(eventoProcesadoRepository.marcarSinoProceso(any(SolicitudAprobadasEvent.class))).thenReturn(Mono.error(exception));

        useCase.procesar(event)
                .as(StepVerifier::create)
                .expectErrorMatches(throwable -> throwable.equals(exception))
                .verify();

        verify(reportesCountRepository, never()).increment(event.getMontoAprobado());
    }
}
