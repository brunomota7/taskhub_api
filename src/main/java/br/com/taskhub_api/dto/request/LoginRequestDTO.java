package br.com.taskhub_api.dto.request;

public record LoginRequestDTO(
        String email,
        String password
) {
}
