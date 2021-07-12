package com.creseliana.repository;

import com.creseliana.model.Chat;
import org.springframework.stereotype.Repository;

@Repository
public class BaseChatRepository extends BaseModelRepository<Chat> implements ChatRepository {
    @Override
    protected Class<Chat> getModelClass() {
        return Chat.class;
    }
}
