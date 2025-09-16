package co.com.crediya.reportes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class CantidadPrestamosAprobados {
    String id_aprobadas;
    long total;
    String fechaActualiza; // formato dynamodb ejemplo: 2023-10-10T10:10:10

    public CantidadPrestamosAprobados() {

    }
}
