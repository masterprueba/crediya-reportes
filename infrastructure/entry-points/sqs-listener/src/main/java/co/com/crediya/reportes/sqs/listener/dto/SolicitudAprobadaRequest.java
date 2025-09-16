package co.com.crediya.reportes.sqs.listener.dto;

public record SolicitudAprobadaRequest(
        String idEvento,
        String idSolicitud,
        String fechaDecidido
) {
}
