package br.com.taskhub_api.dto.request;


public record GroupRequestDTO(
        String groupName,
        String description,
        String typeGroup
) {
}
