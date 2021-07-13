package com.creseliana.controller;

import com.creseliana.dto.UserCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserCreateRequest user) {
        //todo logic
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<Void> profile(Authentication authentication) {
        //todo logic and change return type to new DTO
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> edit(Authentication authentication,
                                     @RequestBody UserCreateRequest user) {
        String username = authentication.getName();
        //todo logic
        return ResponseEntity.noContent().build();
    }
}
