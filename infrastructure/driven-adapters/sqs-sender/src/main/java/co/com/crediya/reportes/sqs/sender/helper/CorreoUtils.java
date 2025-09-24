package co.com.crediya.reportes.sqs.sender.helper;

import co.com.crediya.reportes.model.CantidadPrestamosAprobados;

public class CorreoUtils {

    private CorreoUtils() {
        // Constructor vacío
    }

    public static String construirCuerpoReporteDiario(CantidadPrestamosAprobados cantidadPrestamosAprobados) {
        return String.format(
                """
                        <html>
                        <body>
                            <h1>Reporte Diario de Préstamos Aprobados</h1>
                            <p>Estimado usuario,</p>
                            <p>Le informamos que el total de préstamos aprobados hasta la fecha es: <strong>%d</strong>.</p>
                            <p>El monto total aprobado es: <strong>$%,.2f</strong>.</p>
                            <br/>
                            <p>Que tenga buen dia.</p>
                            <br/>
                            <p>Atentamente,</p>
                            <p>El equipo de CrediYa</p>
                        </body>
                        </html>
                        """,
                cantidadPrestamosAprobados.getTotal(),
                cantidadPrestamosAprobados.getMontoTotalAprobado()
        );
    }

    public static String construirCorreo(String asunto, String cuerpo, String email) {
        return String.format(
                "{\"to\": \"%s\", \"subject\": \"%s\", \"body\": \"%s\"}",
               email,
                asunto,
                cuerpo.replace("\"", "\\\"").replace("\n", "").replace("\r", "")
        );
    }
}
