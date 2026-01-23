package br.com.taskhub_api.service;

import br.com.taskhub_api.dto.request.UserUpdateRequestDTO;
import br.com.taskhub_api.dto.response.UserResponseDTO;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.enums.Role;
import br.com.taskhub_api.exception.CredentialAlreadyExists;
import br.com.taskhub_api.exception.InvalidCredentialsException;
import br.com.taskhub_api.exception.UserNotFoundException;
import br.com.taskhub_api.mapper.UserMapper;
import br.com.taskhub_api.repository.UserRepository;
import br.com.taskhub_api.utility.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;

    public UserService(UserRepository userRepository,
                       SecurityService securityService) {
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    // Busca por todos os usuários da aplicação, apenas para ADMINS
    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    // Busca usuários por ID
    public UserResponseDTO findUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (user.getRole() != Role.ADMIN)
            throw new InvalidCredentialsException();

        return UserMapper.toResponse(user);
    }

    // Pega as informações do usuário autenticado
    public UserResponseDTO getMyInfos() {
        UUID userId = securityService.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        return UserMapper.toResponse(user);
    }

    // Atualiza informações do usuário, apenas para USER
    @Transactional
    public void updateMyInfos(UserUpdateRequestDTO dto) {
        UUID userId = securityService.getAuthenticatedUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());

        if (user.getRole() != Role.USER)
            throw new InvalidCredentialsException();

        user.setName(dto.name());
        user.setEmail(dto.email());
        userRepository.save(user);
    }

    // Promove um usuário comum para um usuário ADMIN, apenas ADMINS
    @Transactional
    public void promoteToAdmin(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getRole() == Role.ADMIN)
            throw new CredentialAlreadyExists();

        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    // Deleta um usuário pelo ID, apenas ADMINS
    @Transactional
    public void deleteUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }


}
