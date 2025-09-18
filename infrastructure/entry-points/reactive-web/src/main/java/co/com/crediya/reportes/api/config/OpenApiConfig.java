package co.com.crediya.reportes.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Reportes de Crédito Crediya")
                        .version("1.0.0")
                        .description("API para el registro de solicitudes de crédito en el sistema Crediya.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo Crediya")
                                .email("jors.castro@gmail.com")
                        )
                );
    }
}
