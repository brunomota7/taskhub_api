package br.com.taskhub_api.dto.response;

import br.com.taskhub_api.entites.User;

import java.util.Set;

public record GroupResponseDTO(
        Long id,
        String groupName,
        String description,
        Set<User> members
) {
}
