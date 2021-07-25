package com.creseliana.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {
    private String senderUsername;
    private String message;
    private LocalDateTime date;
    private boolean isRead;
}
