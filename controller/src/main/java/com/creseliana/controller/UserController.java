package com.creseliana.controller;

import com.creseliana.dto.UserProfileResponse;
import com.creseliana.service.RatingService;
import com.creseliana.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RatingService ratingService;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponse> getProfile(@PathVariable String username) {
        UserProfileResponse user = userService.getByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<Void> rateUser(Authentication authentication,
                                         @PathVariable String username,
                                         @RequestParam int rate) {
        ratingService.rate(authentication.getName(), username, rate);
        return ResponseEntity.noContent().build();
    }
}
