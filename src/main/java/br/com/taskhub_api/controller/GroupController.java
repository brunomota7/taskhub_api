package br.com.taskhub_api.controller;

import br.com.taskhub_api.dto.request.GroupRequestDTO;
import br.com.taskhub_api.dto.response.GroupResponseDTO;
import br.com.taskhub_api.dto.response.MembersGroupResponseDTO;
import br.com.taskhub_api.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody @Valid GroupRequestDTO dto
    ) {
        groupService.createGroup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GroupResponseDTO>> findAll() {
        return ResponseEntity.ok(groupService.findAllGroups());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDTO> findById(
            @PathVariable Long groupId
    ) {
        return ResponseEntity.ok(groupService.findGroupById(groupId));
    }

    @GetMapping(params = "name")
    public ResponseEntity<GroupResponseDTO> findByName(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(groupService.findGroupByName(name));
    }

    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GroupResponseDTO>> findAllByUser(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(groupService.findAllGroupsByUserId(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GroupResponseDTO>> findMyGroups() {
        return ResponseEntity.ok(groupService.findAllMyGroups());
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<MembersGroupResponseDTO> findMembers(
            @PathVariable Long groupId
    ) {
        return ResponseEntity.ok(groupService.findMembersGroupById(groupId));
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<Void> addMember(
            @PathVariable Long groupId,
            @PathVariable UUID userId
    ) {
        groupService.addNewMember(groupId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}/members/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long groupId,
            @PathVariable UUID userId
    ) {
        groupService.removeMemberGroupById(groupId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long groupId
    ) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}
