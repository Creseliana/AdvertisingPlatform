package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentShowResponse {
    private String authorUsername;
    private LocalDateTime date;
    private String comment;
}
