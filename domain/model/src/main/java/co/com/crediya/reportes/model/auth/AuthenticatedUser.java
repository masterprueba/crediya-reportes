package co.com.crediya.reportes.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatedUser {
    private String userId;
    private String email;
    private String role;
    private String token;
}
