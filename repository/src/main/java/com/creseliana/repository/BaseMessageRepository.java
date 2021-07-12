package com.creseliana.repository;

import com.creseliana.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public class BaseMessageRepository extends BaseModelRepository<Message> implements MessageRepository {
    @Override
    protected Class<Message> getModelClass() {
        return Message.class;
    }
}
