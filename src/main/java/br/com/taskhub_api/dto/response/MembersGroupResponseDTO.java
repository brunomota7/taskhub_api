package br.com.taskhub_api.dto.response;

import java.util.UUID;

public record MembersGroupResponseDTO(
        Long groupId,
        Members members
) {

    public record Members(
            UUID id,
            String name
    ) {}

}
