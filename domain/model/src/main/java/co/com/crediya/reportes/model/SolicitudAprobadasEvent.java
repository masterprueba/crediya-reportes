package co.com.crediya.reportes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class SolicitudAprobadasEvent {
    String eventId;
    String solicitudId;
    String fechaProcesada;
    BigDecimal montoAprobado;
    Long ttl;
}
