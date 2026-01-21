package br.com.taskhub_api.service;

import br.com.taskhub_api.dto.request.LoginRequestDTO;
import br.com.taskhub_api.dto.request.RegisterRequestDTO;
import br.com.taskhub_api.dto.response.LoginResponseDTO;
import br.com.taskhub_api.dto.response.RegisterResponseDTO;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.enums.Role;
import br.com.taskhub_api.exception.InvalidCredentialsException;
import br.com.taskhub_api.exception.UserAlreadyExistsException;
import br.com.taskhub_api.mapper.RegisterMapper;
import br.com.taskhub_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtService jwtService,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterResponseDTO toRegister(RegisterRequestDTO dto) {

        if (userRepository.existsByEmail(dto.email()))
            throw new UserAlreadyExistsException(dto.email());

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return RegisterMapper.toRegisterResponse(user);
    }

    public LoginResponseDTO toLogin(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(dto.password(), user.getPassword()))
            throw new InvalidCredentialsException();

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(token, "Bearer");
    }

}
