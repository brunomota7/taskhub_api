package br.com.taskhub_api.mapper;

import br.com.taskhub_api.dto.response.GroupResponseDTO;
import br.com.taskhub_api.dto.response.MembersGroupResponseDTO;
import br.com.taskhub_api.entites.Group;
import br.com.taskhub_api.entites.User;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupResponseDTO toResponse(Group group) {

        User owner = group.getOwner();

        GroupResponseDTO.OwnerDTO ownerDTO =
                new GroupResponseDTO.OwnerDTO(
                        owner.getName(),
                        owner.getEmail()
                );

        return new GroupResponseDTO(
                group.getGroupId(),
                group.getGroupName(),
                group.getDescription(),
                group.getTypeGroup(),
                group.getTasks().size(),
                ownerDTO
        );
    }

    public static MembersGroupResponseDTO toMembersGroupResponse(Group group) {

        Set<MembersGroupResponseDTO.MemberDTO> members =
                group.getUsers()
                        .stream()
                        .map(user -> new MembersGroupResponseDTO.MemberDTO(
                                user.getUserId(),
                                user.getName()
                        ))
                        .collect(Collectors.toSet());

        return new MembersGroupResponseDTO(
                group.getGroupId(),
                members
        );
    }

}
