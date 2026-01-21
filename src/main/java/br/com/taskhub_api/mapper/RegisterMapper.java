package br.com.taskhub_api.mapper;

import br.com.taskhub_api.dto.response.RegisterResponseDTO;
import br.com.taskhub_api.entites.User;

public class RegisterMapper {

    public static RegisterResponseDTO toRegisterResponse(User user) {
        var userId = user.getUserId();
        var name = user.getName();
        var email = user.getEmail();
        var role = user.getRole();

        var response = new RegisterResponseDTO(userId, name, email, role);

        return response;
    }

}
