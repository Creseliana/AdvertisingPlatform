package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserProfileResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean isActive;
    private LocalDateTime registrationDate;
    private BigDecimal rating;
}
