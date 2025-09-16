package co.com.crediya.reportes.dynamodb.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class PrestamosAprobadosEntity {

    private String id_aprobadas;
    private Long total;
    private String fechaActualiza;


    public PrestamosAprobadosEntity() {
    }

    public PrestamosAprobadosEntity(String id_aprobadas, Long total, String fechaActualiza) {
        this.id_aprobadas = id_aprobadas;
        this.total = total;
        this.fechaActualiza = fechaActualiza;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id_aprobadas")
    public String getId_aprobadas() {
        return id_aprobadas;
    }

    public void setId_aprobadas(String id_aprobadas) {
        this.id_aprobadas = id_aprobadas;
    }

    @DynamoDbAttribute("total")
    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @DynamoDbAttribute("fecha_actualiza")
    public String getFechaActualiza() {
        return this.fechaActualiza;
    }

    public void setFechaActualiza(String fechaActualiza) {
        this.fechaActualiza = fechaActualiza;
    }
}
