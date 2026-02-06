package br.com.taskhub_api.dto.request;

import br.com.taskhub_api.entites.Group;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.enums.StatusTask;

import java.time.LocalDate;
import java.util.UUID;

public record TaskRequestDTO(
        String task,
        String description,
        LocalDate dueDate,
        Long groupId
) {
}
