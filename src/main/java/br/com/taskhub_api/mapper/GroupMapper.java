package br.com.taskhub_api.mapper;

import br.com.taskhub_api.dto.response.GroupResponseDTO;
import br.com.taskhub_api.dto.response.MembersGroupResponseDTO;
import br.com.taskhub_api.entites.Group;
import br.com.taskhub_api.entites.Task;
import br.com.taskhub_api.entites.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupResponseDTO toResponse(Group group) {

        Set<User> users = group.getUsers();

        var id = group.getGroupId();
        var groupName = group.getGroupName();
        var description = group.getDescription();

        var res = new GroupResponseDTO(id, groupName, description, users);

        return res;
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
