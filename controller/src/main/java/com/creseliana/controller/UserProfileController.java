package com.creseliana.controller;

import com.creseliana.dto.UserCreateRequest;
import com.creseliana.dto.UserEditRequest;
import com.creseliana.dto.UserProfileResponse;
import com.creseliana.service.UserService;
import com.creseliana.tracker.annotation.Track;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserProfileController {

    private final UserService userService;

    @Track
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserCreateRequest user,
                                       @RequestParam(required = false) String role) {
        userService.create(user, role);
        return ResponseEntity.noContent().build();
    }

    @Track
    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(Authentication authentication) { //AuthenticationPrincipal User user
        UserProfileResponse user = userService.getByUsername(authentication.getName());

        return ResponseEntity.ok(user);
    }

    @PatchMapping
    public ResponseEntity<Void> edit(Authentication authentication,
                                     @Valid @RequestBody UserEditRequest user) {
        userService.edit(authentication.getName(), user);
        authentication.setAuthenticated(false);
        return ResponseEntity.noContent().build();
    }
}
