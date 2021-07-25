package com.creseliana.repository;

import com.creseliana.model.Message;

import java.util.List;

public interface MessageRepository extends ModelRepository<Message, Long> {

    List<Message> getMessagesByChatId(Long id, int start, int amount);
}
