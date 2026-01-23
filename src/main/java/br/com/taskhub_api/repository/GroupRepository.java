package br.com.taskhub_api.repository;

import br.com.taskhub_api.entites.Group;
import br.com.taskhub_api.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupName(String groupName);
    List<Group> findAllByUsers_UserId(UUID userId);
    boolean existsByGroupName(String groupName);
}
