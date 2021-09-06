package com.creseliana.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestGeneratorController {
    private static final String PATH_GENERATOR_LIMIT = "http://localhost:8090/gen?limit=%s";
    private static final String MSG_LUCKY_NUMBER = "Your lucky number is %s";

    private final RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<String> getLuckyNumber(@RequestParam int limit) {
        ResponseEntity<Integer> entity = restTemplate
                .getForEntity(String.format(PATH_GENERATOR_LIMIT, limit), Integer.class);
        String line = String.format(MSG_LUCKY_NUMBER, entity.getBody());
        return ResponseEntity.ok(line);
    }
}
