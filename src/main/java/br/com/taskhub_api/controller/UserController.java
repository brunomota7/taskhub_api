package br.com.taskhub_api.controller;

import br.com.taskhub_api.dto.request.UserUpdateRequestDTO;
import br.com.taskhub_api.dto.response.UserResponseDTO;
import br.com.taskhub_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> findById(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        return ResponseEntity.ok(userService.getMyInfos());
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateMe(
            @RequestBody @Valid UserUpdateRequestDTO dto
    ) {
        userService.updateMyInfos(dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> promoteToAdmin(
            @PathVariable UUID userId
    ) {
        userService.promoteToAdmin(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable UUID userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
