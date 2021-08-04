package com.creseliana.controller;

import com.creseliana.dto.AdvertisementCreateRequest;
import com.creseliana.dto.AdvertisementDetailedResponse;
import com.creseliana.dto.AdvertisementEditRequest;
import com.creseliana.dto.AdvertisementPreviewResponse;
import com.creseliana.dto.AdvertisementShortResponse;
import com.creseliana.dto.CommentShowResponse;
import com.creseliana.service.AdvertisementService;
import com.creseliana.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
public class AdvertisementController {

    private final AdvertisementService adService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> create(Authentication authentication,
                                       @RequestBody AdvertisementCreateRequest ad) {
        adService.create(authentication.getName(), ad);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AdvertisementPreviewResponse>> getAll(@RequestParam int page,
                                                                     @RequestParam int amount) {
        List<AdvertisementPreviewResponse> ads = adService.getAll("", page, amount);
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDetailedResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(adService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> edit(Authentication authentication,
                                     @PathVariable Long id,
                                     @Valid @RequestBody AdvertisementEditRequest ad) {
        adService.edit(authentication.getName(), id, ad);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> close(Authentication authentication,
                                      @PathVariable Long id) {
        adService.close(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication authentication,
                                       @PathVariable Long id) {
        adService.delete(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/payment/{id}")
    public ResponseEntity<Void> pay(Authentication authentication,
                                    @PathVariable Long id) {
        adService.pay(authentication.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed/{username}")
    public ResponseEntity<List<AdvertisementShortResponse>> getCompleted(@PathVariable String username,
                                                                         @RequestParam int page,
                                                                         @RequestParam int amount) {
        List<AdvertisementShortResponse> ads = adService.getCompletedByUsername(username, page, amount);
        return ResponseEntity.ok(ads);
    }

    @GetMapping("/incomplete/{username}")
    public ResponseEntity<List<AdvertisementShortResponse>> getIncomplete(@PathVariable String username,
                                                                          @RequestParam int page,
                                                                          @RequestParam int amount) {
        List<AdvertisementShortResponse> ads = adService.getIncompleteByUsername(username, page, amount);
        return ResponseEntity.ok(ads);
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<Void> createComment(Authentication authentication,
                                              @PathVariable Long id,
                                              @RequestBody String comment) {
        commentService.create(authentication.getName(), id, comment);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentShowResponse>> getComments(@PathVariable Long id,
                                                                 @RequestParam int page,
                                                                 @RequestParam int amount) {
        List<CommentShowResponse> comments = commentService.getAll(id, page, amount);
        return ResponseEntity.ok(comments);
    }
}
