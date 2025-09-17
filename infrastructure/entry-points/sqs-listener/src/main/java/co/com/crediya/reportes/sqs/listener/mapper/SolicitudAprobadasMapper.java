package co.com.crediya.reportes.sqs.listener.mapper;

import co.com.crediya.reportes.model.SolicitudAprobadasEvent;
import co.com.crediya.reportes.sqs.listener.dto.SolicitudAprobadaRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SolicitudAprobadasMapper {

    @Mapping(source = "idEvento", target = "eventId")
    @Mapping(source = "idSolicitud", target = "solicitudId")
    @Mapping(source = "fechaDecidido", target = "fechaProcesada")
    @Mapping(source = "montoAprobado", target = "montoAprobado")
    @Mapping(target = "ttl", ignore = true)
    SolicitudAprobadasEvent toModel(SolicitudAprobadaRequest r);
}
