package com.creseliana.controller;

import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import com.creseliana.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserProfileController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserCreateRequest user,
                                       @RequestParam(required = false) String role) {
        userService.create(user, role);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) {
        UserProfileResponse user = userService.getByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/edit")
    public ResponseEntity<Void> edit(Authentication authentication,
                                     @RequestBody UserEditRequest user) {
        userService.edit(authentication.getName(), user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Working 1");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("Working 2");
    }
}
