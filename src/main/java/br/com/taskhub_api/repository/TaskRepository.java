package br.com.taskhub_api.repository;

import br.com.taskhub_api.entites.Task;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.enums.StatusTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByTaskName(String taskName);
    List<Task> findByStatus(StatusTask statusTask);
    List<Task> findByResponsible(User user);
    List<Task> findByResponsibleUserId(UUID userId);
}
