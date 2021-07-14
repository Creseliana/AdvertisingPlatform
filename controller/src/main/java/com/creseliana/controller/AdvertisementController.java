package com.creseliana.controller;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementShowResponse;
import com.creseliana.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdvertisementController {
    private final AdvertisementService adService;

    @PostMapping
    public ResponseEntity<Void> create(Authentication authentication,
                                       @RequestBody AdvertisementCreateRequest ad) {
        adService.create(authentication.getName(), ad);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/editing/{id}")
    public ResponseEntity<Void> edit(Authentication authentication,
                                     @PathVariable Long id,
                                     @RequestBody AdvertisementEditRequest ad) {
        adService.edit(authentication.getName(), id, ad);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("deletion/{id}")
    public ResponseEntity<Void> delete(Authentication authentication,
                                       @PathVariable Long id) {
        adService.delete(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/closing/{id}")
    public ResponseEntity<Void> close(Authentication authentication,
                                      @PathVariable Long id) {
        adService.close(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementShowResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(adService.show(id));
    }
}
