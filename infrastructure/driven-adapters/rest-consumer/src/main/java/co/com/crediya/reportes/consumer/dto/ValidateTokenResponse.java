package co.com.crediya.reportes.consumer.dto;

public record ValidateTokenResponse(
    String userId,
    String email,
    String role
) {}
