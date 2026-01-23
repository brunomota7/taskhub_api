package br.com.taskhub_api.dto.response;

public record GroupResponseDTO(
        Long id,
        String groupName,
        String description,
        OwnerDTO owner
) {

    public record OwnerDTO(
            String name,
            String email
    ) {}
}

