package co.com.crediya.reportes.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportesResponseDto(
        @Schema(description = "Total de solicitudes aprobadas", example = "1234")
        long totalAprobadas,
        @Schema(description = "Monto total aprobado", example = "1500000.75")
        double montoTotalAprobado,
        @Schema(description = "Última actualización (ISO-8601)", example = "2025-09-11T12:34:56Z")
        String updatedAt
) {
}
