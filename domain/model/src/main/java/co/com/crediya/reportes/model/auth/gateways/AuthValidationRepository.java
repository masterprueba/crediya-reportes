package co.com.crediya.reportes.model.auth.gateways;

import co.com.crediya.reportes.model.auth.AuthenticatedUser;
import reactor.core.publisher.Mono;

public interface AuthValidationRepository {
    Mono<AuthenticatedUser> validateToken(String token);
}
