package co.com.crediya.reportes.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReportesResponseDto(
        @Schema(description = "Total de solicitudes aprobadas", example = "1234")
        long totalAprobadas,
        @Schema(description = "Última actualización (ISO-8601)", example = "2025-09-11T12:34:56Z")
        String updatedAt
) {
}
