package br.com.taskhub_api.dto.response;

import br.com.taskhub_api.entites.User;

import java.util.Set;
import java.util.UUID;

public record GroupResponseDTO(
        Long id,
        String groupName,
        String description,
        Set<User> members,
        OwnerDTO owner
) {

    public record OwnerDTO(
            String name,
            String email
    ) {}

}
