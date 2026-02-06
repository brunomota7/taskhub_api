package br.com.taskhub_api.controller;

import br.com.taskhub_api.dto.request.LoginRequestDTO;
import br.com.taskhub_api.dto.request.RegisterRequestDTO;
import br.com.taskhub_api.dto.response.LoginResponseDTO;
import br.com.taskhub_api.dto.response.RegisterResponseDTO;
import br.com.taskhub_api.mapper.RegisterMapper;
import br.com.taskhub_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> toRegister(
            @RequestBody @Valid RegisterRequestDTO dto
    ) {
        authService.toRegister(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> toLogin(
            @RequestBody @Valid LoginRequestDTO dto
    ) {
        LoginResponseDTO response = authService.toLogin(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
