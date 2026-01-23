package br.com.taskhub_api.dto.request;

public record UserUpdateRequestDTO(
        String name,
        String email
) {
}
