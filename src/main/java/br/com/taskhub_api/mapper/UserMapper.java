package br.com.taskhub_api.mapper;

import br.com.taskhub_api.dto.response.UserResponseDTO;
import br.com.taskhub_api.entites.User;

public class UserMapper {

    public static UserResponseDTO toResponse(User user) {
        var id = user.getUserId();
        var name = user.getName();
        var email = user.getEmail();
        var role = user.getRole();

        var res = new UserResponseDTO(id, name, email, role);

        return res;
    }

}
