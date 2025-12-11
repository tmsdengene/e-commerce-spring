package com.gibesystems.ecommerce.auth.service;

import com.gibesystems.ecommerce.auth.dto.CreateUserDTO;
import com.gibesystems.ecommerce.auth.dto.UpdateUserDTO;
import com.gibesystems.ecommerce.auth.dto.UserDTO;
import com.gibesystems.ecommerce.auth.entity.User;
import java.util.Collections;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
       
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public User toEntity(CreateUserDTO request) {
        if (request == null) return null;
        User.UserBuilder builder = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(request.getPassword());
        return builder.build();
    }

    public void updateEntity(UpdateUserDTO request, User user) {
        if (request == null || user == null) return;
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
    }

}
