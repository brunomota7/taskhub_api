package br.com.taskhub_api.dto.response;

import br.com.taskhub_api.enums.Role;

import java.util.UUID;

public record RegisterResponseDTO(
        UUID id,
        String name,
        String email,
        Role role
) {
}
