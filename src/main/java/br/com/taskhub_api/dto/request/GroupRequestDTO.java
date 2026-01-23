package br.com.taskhub_api.dto.request;

import br.com.taskhub_api.entites.User;

public record GroupRequestDTO(
        String groupName,
        String description,
        User user
) {
}
