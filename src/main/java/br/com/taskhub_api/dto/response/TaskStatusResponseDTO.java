package br.com.taskhub_api.dto.response;

import br.com.taskhub_api.enums.StatusTask;

public record TaskStatusResponseDTO(
        Long id,
        String task,
        StatusTask status
) {
}
