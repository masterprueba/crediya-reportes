package co.com.crediya.reportes.sqs.listener.dto;

import java.math.BigDecimal;

public record SolicitudAprobadaRequest(
        String idEvento,
        String idSolicitud,
        String fechaDecidido,
        BigDecimal montoAprobado
) {
}
