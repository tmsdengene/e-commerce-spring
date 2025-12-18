package com.gibesystems.ecommerce.auth.dto;

import java.time.LocalDateTime;

import com.gibesystems.ecommerce.auth.entity.UserRole;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private boolean enabled;
    private LocalDateTime createdAt;

}