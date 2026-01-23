package br.com.taskhub_api.service;

import br.com.taskhub_api.dto.request.GroupRequestDTO;
import br.com.taskhub_api.dto.response.GroupResponseDTO;
import br.com.taskhub_api.dto.response.MembersGroupResponseDTO;
import br.com.taskhub_api.entites.Group;
import br.com.taskhub_api.entites.User;
import br.com.taskhub_api.exception.*;
import br.com.taskhub_api.mapper.GroupMapper;
import br.com.taskhub_api.repository.GroupRepository;
import br.com.taskhub_api.repository.UserRepository;
import br.com.taskhub_api.utility.SecurityService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    public GroupService(GroupRepository groupRepository,
                        UserRepository userRepository,
                        SecurityService securityService) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    private User getAuthenticatedUser() {
        return userRepository.findById(
                securityService.getAuthenticatedUserId()
        ).orElseThrow(UserNotFoundException::new);
    }

    // Cria um novo grupo
    @Transactional
    public void createGroup(GroupRequestDTO dto) {
        User user = getAuthenticatedUser();

        if (groupRepository.existsByGroupName(dto.groupName()))
            throw new GroupAlreadyExistsException();

        Group group = new Group();
        group.setGroupName(dto.groupName());
        group.setDescription(dto.description());
        group.setOwner(user);

        group.getUsers().add(user);
        user.getGroups().add(group);

        groupRepository.save(group);
    }

    // Adiciona um novo membro ao grupo, apenas AMDINS e proprietário do grupo
    @Transactional
    public void addNewMember(Long groupId, UUID userId) {
        User currentUser = getAuthenticatedUser();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        group.assertOwner(currentUser);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!group.getUsers().add(user)) {
            throw new UserAlreadyExistsException();
        }

        user.getGroups().add(group);
    }


    // Busca todos os grupos, apenas ADMINS
    public List<GroupResponseDTO> findAllGroups() {
        return groupRepository.findAll()
                .stream()
                .map(GroupMapper::toResponse)
                .toList();
    }

    // Busca grupo por ID, apenas ADMINS
    public GroupResponseDTO findGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);

        return GroupMapper.toResponse(group);
    }

    // Busca grupo pelo nome
    public GroupResponseDTO findGroupByName(String name) {
        Group group = groupRepository.findByGroupName(name)
                .orElseThrow(GroupNotFoundException::new);

        return GroupMapper.toResponse(group);
    }

    // Busca todos os grupos de um usuário pelo ID, apenas ADMINS
    public List<GroupResponseDTO> findAllGroupsByUserId(UUID userId) {
        return groupRepository.findAllByUsers_UserId(userId)
                .stream()
                .map(GroupMapper::toResponse)
                .toList();
    }

    // Busca todos os grupos do usuário autenticado
    public List<GroupResponseDTO> findAllMyGroups() {
        User user = getAuthenticatedUser();

        return user.getGroups().stream()
                .map(GroupMapper::toResponse)
                .toList();
    }

    // Busca todos os membros de um grupo
    public MembersGroupResponseDTO findMembersGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(GroupNotFoundException::new);

        return GroupMapper.toMembersGroupResponse(group);
    }

    // Remove um usuário de um grupo, apenas AMDINS e proprietário do grupo
    @Transactional
    public void removeMemberGroupById(Long groupId, UUID userId) {
        User currentUser = getAuthenticatedUser();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        group.assertOwner(currentUser);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!group.getUsers().remove(user)) {
            throw new UserNotMemberOfGroupException();
        }

        user.getGroups().remove(group);
    }

    // Deleta um grupo, apenas AMDINS e proprietário do grupo
    @Transactional
    public void deleteGroup(Long groupId) {
        User currentUser = getAuthenticatedUser();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(GroupNotFoundException::new);

        group.assertOwner(currentUser);

        group.getUsers().forEach(user -> user.getGroups().remove(group));
        group.getUsers().clear();

        groupRepository.delete(group);
    }

}
