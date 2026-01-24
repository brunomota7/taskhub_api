package br.com.taskhub_api.service;

import br.com.taskhub_api.dto.request.TaskRequestDTO;
import br.com.taskhub_api.dto.request.TaskUpdateDTO;
import br.com.taskhub_api.dto.response.TaskResponseDTO;
import br.com.taskhub_api.dto.response.TaskStatusResponseDTO;
import br.com.taskhub_api.entites.Task;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.enums.StatusTask;
import br.com.taskhub_api.exception.TaskNotFoundException;
import br.com.taskhub_api.exception.UserNotFoundException;
import br.com.taskhub_api.mapper.TaskMapper;
import br.com.taskhub_api.repository.TaskRepository;
import br.com.taskhub_api.repository.UserRepository;
import br.com.taskhub_api.utility.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       SecurityService securityService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    private User getAuthenticatedUser() {
        return userRepository.findById(
                securityService.getAuthenticatedUserId()
        ).orElseThrow(UserNotFoundException::new);
    }

    // Cria uma nova Taks
    @Transactional
    public void createNewTask(TaskRequestDTO dto) {
        User user = getAuthenticatedUser();

        Task task = new Task();
        task.setTaskName(dto.task());
        task.setDescription(dto.description());
        task.setStatus(StatusTask.PENDING);
        task.setDueDate(dto.dueDate());
        task.setGroup(dto.group());
        task.setResponsible(user);

        user.getTasks().add(task);

        userRepository.save(user);
        taskRepository.save(task);
    }

    // Busca por todas as task do sistema, apenas ADMINS
    public List<TaskResponseDTO> findAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    // Busca task pelo ID
    public TaskResponseDTO findTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);

        return TaskMapper.toResponse(task);
    }

    // Busca task pelo nome
    public TaskResponseDTO findTaskByName(String taskName) {
        Task task = taskRepository.findByTaskName(taskName)
                .orElseThrow(TaskNotFoundException::new);

        return TaskMapper.toResponse(task);
    }

    // Busca todas as atividades de um usuário pelo ID (user ID), apenas ADMINS
    public List<TaskResponseDTO> findAllTasksByUserId(UUID userId) {
        return taskRepository.findByResponsible_UserId(userId)
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    // Busca todas as atividades do usuário autenticado
    public List<TaskResponseDTO> findAllMyTasks() {
        User user = getAuthenticatedUser();

        return user.getTasks().stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    // Busca tasks pelo status
    public List<TaskStatusResponseDTO> findAllTaskByStatus(StatusTask status) {
        return taskRepository.findByStatus(status)
                .stream()
                .map(TaskMapper::toResponseStatus)
                .toList();
    }

    // Busca as tasks com base em uma data informada
    public List<TaskResponseDTO> findAllTasksByDueDate(LocalDate dueDate) {
        List<Task> tasks = taskRepository.findByDueDate(dueDate);
        return tasks.stream()
                .map(TaskMapper::toResponse)
                .toList();
    }

    // Atualiza as informações gerais de uma task
    @Transactional
    public void updateTask(Long idTask, TaskUpdateDTO dto) {
        Task task = taskRepository.findById(idTask)
                .orElseThrow(TaskNotFoundException::new);

        task.setTaskName(dto.task());
        task.setDescription(dto.description());
        task.setDueDate(dto.dueDate());

        taskRepository.save(task);
    }

    // Atualiza o status de uma task
    @Transactional
    public void updateStatusTask(Long idTask, StatusTask statusTask) {
        Task task = taskRepository.findById(idTask)
                .orElseThrow(TaskNotFoundException::new);

        task.setStatus(statusTask);
        taskRepository.save(task);
    }

    // Deleta uma task pelo ID
    @Transactional
    public void deleteTaskById(Long idTask) {
        Task task = taskRepository.findById(idTask)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.delete(task);
    }

}
