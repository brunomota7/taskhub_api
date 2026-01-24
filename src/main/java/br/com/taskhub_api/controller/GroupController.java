package br.com.taskhub_api.controller;

import br.com.taskhub_api.dto.request.GroupRequestDTO;
import br.com.taskhub_api.dto.response.GroupResponseDTO;
import br.com.taskhub_api.dto.response.MembersGroupResponseDTO;
import br.com.taskhub_api.service.GroupService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createGroup(
            @RequestBody @Valid GroupRequestDTO dto
    ) {
        groupService.createGroup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{groupId}/add/{userid}")
    public ResponseEntity<Void> addUserToGroup(
            @PathVariable Long groupId,
            @PathVariable UUID userId
    ) {
        groupService.addNewMember(groupId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GroupResponseDTO>> findAllGroups() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(groupService.findAllGroups());
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDTO> findGroupById(
            @PathVariable Long groupId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(groupService.findGroupById(groupId));
    }

    @GetMapping("/{name}")
    public ResponseEntity<GroupResponseDTO> findGroupByName(
            @PathVariable String name
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(groupService.findGroupByName(name));
    }

    @GetMapping("/{userId}/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GroupResponseDTO>> findAllGroupsByUserId(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(groupService.findAllGroupsByUserId(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GroupResponseDTO>> findAllMyGroups() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(groupService.findAllMyGroups());
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<MembersGroupResponseDTO>> findAllMembersByGroupId(
            @PathVariable Long groupId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonList(groupService.findMembersGroupById(groupId)));
    }

    @PutMapping("/{groupId}/remove/{userId}")
    public ResponseEntity<Void> removeMemberFromGroup(
            @PathVariable Long groupId,
            @PathVariable UUID userId
    ) {
        groupService.removeMemberGroupById(groupId, userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{groupId}/delete")
    public ResponseEntity<Void> deleteGroup(
            @PathVariable Long groupId
    ) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
