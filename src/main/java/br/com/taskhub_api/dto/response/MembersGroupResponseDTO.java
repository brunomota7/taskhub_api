package br.com.taskhub_api.dto.response;

import java.util.Set;
import java.util.UUID;

public record MembersGroupResponseDTO(
        Long groupId,
        Set<MemberDTO> members
) {

    public record MemberDTO(
            UUID id,
            String name
    ) {}

}
