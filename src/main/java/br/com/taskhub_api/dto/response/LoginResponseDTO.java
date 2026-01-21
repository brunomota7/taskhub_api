package br.com.taskhub_api.dto.response;

public record LoginResponseDTO(
        String token,
        String type
) {
}
