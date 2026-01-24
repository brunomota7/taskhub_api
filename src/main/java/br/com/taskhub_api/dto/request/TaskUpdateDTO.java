package br.com.taskhub_api.dto.request;

import java.time.LocalDate;

public record TaskUpdateDTO(
        String task,
        String description,
        LocalDate dueDate
) {
}
