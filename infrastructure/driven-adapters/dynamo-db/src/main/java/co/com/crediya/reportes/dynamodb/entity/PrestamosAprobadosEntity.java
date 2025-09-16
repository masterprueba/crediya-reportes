package co.com.crediya.reportes.dynamodb.entity;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/
@DynamoDbBean
public class PrestamosAprobadosEntity {

    private String idAprobadas;
    private Long total;
    private String fechaActualiza;


    public PrestamosAprobadosEntity() {
    }

    public PrestamosAprobadosEntity(String idAprobadas, Long total, String fechaActualiza) {
        this.idAprobadas = idAprobadas;
        this.total = total;
        this.fechaActualiza = fechaActualiza;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id_aprobadas")
    public String getIdAprobadas() {
        return idAprobadas;
    }

    public void setIdAprobadas(String idAprobadas) {
        this.idAprobadas = idAprobadas;
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
