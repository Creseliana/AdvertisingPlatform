package com.creseliana.repository;

import com.creseliana.model.Chat;

import java.util.Optional;

public interface ChatRepository extends ModelRepository<Chat, Long> {

    Optional<Chat> getChatByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId);
}
