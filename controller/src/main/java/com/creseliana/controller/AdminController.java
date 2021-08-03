package com.creseliana.controller;

import com.creseliana.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/categories")
    public ResponseEntity<Void> addCategory(@RequestParam String name) {
        adminService.addCategory(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<Void> blockUser(@PathVariable String username) {
        adminService.blockUser(username);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{username}")
    public ResponseEntity<Void> activateAccount(@PathVariable String username) {
        adminService.activateAccount(username);
        return ResponseEntity.noContent().build();
    }
}
