package br.com.taskhub_api.dto.request;

import br.com.taskhub_api.entites.Group;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.enums.StatusTask;

import java.time.LocalDate;

public record TaskRequestDTO(
        String task,
        String description,
        StatusTask status,
        LocalDate dueDate,
        Group group,
        User assignedUserId
) {
}
