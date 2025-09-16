package co.com.crediya.reportes.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolicitudAprobadasEvent {
    String eventId;
    String solicitudId;
    String fechaProcesada;
    Long ttl;
}
