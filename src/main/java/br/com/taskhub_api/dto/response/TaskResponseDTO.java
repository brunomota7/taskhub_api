package br.com.taskhub_api.dto.response;

import br.com.taskhub_api.enums.StatusTask;

import java.time.LocalDate;
import java.util.UUID;

public record TaskResponseDTO(
        Long id,
        String task,
        String description,
        StatusTask status,
        LocalDate dueDate,
        Long groupId,
        Responsible responsible
) {
    public record Responsible(
            UUID id,
            String name
    ) {}
}
