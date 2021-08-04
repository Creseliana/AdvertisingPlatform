package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * DTO of the {@link com.creseliana.model.User}
 * Useful for edit existing user information
 * All fields must be filled with old or new data
 */
@Getter
@Setter
public class UserEditRequest {
    @NotEmpty
    @NotBlank
    private String username;
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;
    @Email
    @NotEmpty
    @NotBlank
    private String email;
    @NotEmpty
    @NotBlank
    private String phoneNumber;
}
