package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class UserCreateRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;
}
