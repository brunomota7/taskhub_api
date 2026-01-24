package br.com.taskhub_api.controller;

import br.com.taskhub_api.dto.request.TaskRequestDTO;
import br.com.taskhub_api.dto.request.TaskUpdateDTO;
import br.com.taskhub_api.dto.response.TaskResponseDTO;
import br.com.taskhub_api.dto.response.TaskStatusResponseDTO;
import br.com.taskhub_api.enums.StatusTask;
import br.com.taskhub_api.service.TaskService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> createTask(
            @RequestBody @Valid TaskRequestDTO dto
    ) {
        taskService.createNewTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        return ResponseEntity.ok(taskService.findAllTasks());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> findById(
            @PathVariable Long taskId
    ) {
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<TaskResponseDTO> findByName(
            @PathVariable String name
    ) {
        return ResponseEntity.ok(taskService.findTaskByName(name));
    }

    @GetMapping("/by-user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TaskResponseDTO>> findByUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(taskService.findAllTasksByUserId(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<TaskResponseDTO>> findMyTasks() {
        return ResponseEntity.ok(taskService.findAllMyTasks());
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<TaskStatusResponseDTO>> findByStatus(
            @PathVariable StatusTask status
    ) {
        return ResponseEntity.ok(taskService.findAllTaskByStatus(status));
    }

    @GetMapping("/by-due-date/{dueDate}")
    public ResponseEntity<List<TaskResponseDTO>> findByDueDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dueDate
    ) {
        return ResponseEntity.ok(taskService.findAllTasksByDueDate(dueDate));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable Long taskId,
            @RequestBody @Valid TaskUpdateDTO dto
    ) {
        taskService.updateTask(taskId, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long taskId,
            @RequestParam StatusTask status
    ) {
        taskService.updateStatusTask(taskId, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long taskId
    ) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }
}
