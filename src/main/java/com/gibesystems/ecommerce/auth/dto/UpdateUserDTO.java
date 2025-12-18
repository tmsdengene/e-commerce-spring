package com.gibesystems.ecommerce.auth.dto;

import lombok.Getter;
import lombok.Setter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {
    private String firstName;
    private String lastName;
    private String email;

}