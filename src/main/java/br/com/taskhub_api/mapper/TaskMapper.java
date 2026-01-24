package br.com.taskhub_api.mapper;

import br.com.taskhub_api.dto.response.TaskResponseDTO;
import br.com.taskhub_api.dto.response.TaskStatusResponseDTO;
import br.com.taskhub_api.entites.Task;
import br.com.taskhub_api.entites.User;

public class TaskMapper {

    public static TaskResponseDTO toResponse(Task task) {

        User responsible = task.getResponsible();

        TaskResponseDTO.Responsible responsibleDTO =
                new TaskResponseDTO.Responsible(
                        responsible.getUserId(),
                        responsible.getName()
                );

        return new TaskResponseDTO(
                task.getTaskId(),
                task.getTaskName(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate(),
                task.getGroup().getGroupId(),
                responsibleDTO
        );
    }

    public static TaskStatusResponseDTO toResponseStatus(Task task) {
        return new TaskStatusResponseDTO(
                task.getTaskId(),
                task.getTaskName(),
                task.getStatus()
        );
    }

}
