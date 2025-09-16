package co.com.crediya.reportes.api.mapper;

import co.com.crediya.reportes.api.dto.ReportesResponseDto;
import co.com.crediya.reportes.model.CantidadPrestamosAprobados;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReportesResponseMapper {
    @Mapping(source = "total", target = "totalAprobadas")
    @Mapping(source = "fechaActualiza", target = "updatedAt")
    ReportesResponseDto toResponse( CantidadPrestamosAprobados cantidadPrestamosAprobados);
}
