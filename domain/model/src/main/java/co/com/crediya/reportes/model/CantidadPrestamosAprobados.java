package co.com.crediya.reportes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
public class CantidadPrestamosAprobados {
    String idAprobadas;
    long total;
    String fechaActualiza;
    BigDecimal montoTotalAprobado;

    public CantidadPrestamosAprobados() {
        // Constructor vacío
    }
}
