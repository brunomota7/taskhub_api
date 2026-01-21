package br.com.taskhub_api.dto.request;

public record RegisterRequestDTO(
        String name,
        String email,
        String password
) {
}
