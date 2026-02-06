package br.com.taskhub_api.dto.response;

public record GroupResponseDTO(
        Long id,
        String groupName,
        String description,
        String typeGroup,
        Integer qunatTask,
        OwnerDTO owner
) {

    public record OwnerDTO(
            String name,
            String email
    ) {}
}

