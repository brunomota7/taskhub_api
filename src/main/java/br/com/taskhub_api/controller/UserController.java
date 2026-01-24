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

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> findUserById(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findUserById(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMyInfos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getMyInfos());
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateMyInfos(
            @RequestBody @Valid UserUpdateRequestDTO dto
    ) {
        userService.updateMyInfos(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userId}/promote")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> promoteUserToAdmin(
            @PathVariable UUID userId
    ) {
        userService.promoteToAdmin(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUsesrById(
            @PathVariable UUID userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
