package co.com.crediya.reportes.dynamodb.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;


@DynamoDbBean
public class EventoProcesadoEntity {

    private String eventId;
    private String fechaProcesada;
    private Long ttl;
    private String solicitudId;

    public EventoProcesadoEntity() {
    }

    public EventoProcesadoEntity(String eventId, String fechaProcesada, Long ttl, String solicitudId) {
        this.eventId = eventId;
        this.fechaProcesada = fechaProcesada;
        this.ttl = ttl;
        this.solicitudId = solicitudId;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("eventId")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @DynamoDbAttribute("fechaProcesada")
    public String getFechaProcesada() {
        return this.fechaProcesada;
    }

    public void setFechaProcesada(String fechaProcesada) {
        this.fechaProcesada = fechaProcesada;
    }

    @DynamoDbAttribute("ttl")
    public Long getTtl() {
        return this.ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    @DynamoDbAttribute("solicitudId")
    public String getSolicitudId() {
        return this.solicitudId;
    }

    public void setSolicitudId(String solicitudId) {
        this.solicitudId = solicitudId;
    }
}
