package co.com.crediya.reportes.api;

import co.com.crediya.reportes.api.dto.ReportesResponseDto;
import co.com.crediya.reportes.usecase.consultartotalaprobadas.ConsultarTotalAprobadasUseCase;
import co.com.crediya.reportes.api.mapper.ReportesResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Operaciones ralacionadas con los reportes de las solicitudes")
public class Handler {

private final ConsultarTotalAprobadasUseCase useCase;
private final ReportesResponseMapper reportesResponseMapper;

    @Operation(
            summary = "Reporte total de prestamos",
            description = "Obtiene datos totales de los prestamos aprobados. " +
                    "IMPORTANTE: Solo usuarios con rol ADMIN pueden acceder a este endpoint.",
            operationId = "listenGETConsultarTotalAprobadas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Obtiene reporte de los prestamos aprobados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportesResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = """
                    {
                      "status": 500,
                      "codigo": "ERROR_INTERNO",
                      "mensaje": "Ha ocurrido un error inesperado en el sistema. Por favor, contacte a soporte.",
                      "ruta": "/api/v1/solicitud/listar"
                    }
                    """)
                    )
            )
    })
    public Mono<ServerResponse> listenGETConsultarTotalAprobadas(ServerRequest serverRequest) {
        return  useCase.consultarAprobadas()
                .map(reportesResponseMapper::toResponse)
                .flatMap(result -> ServerResponse.ok().bodyValue(result))
                .switchIfEmpty(ServerResponse.noContent().build());
    }
}
