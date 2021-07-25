package com.creseliana.repository;

import com.creseliana.model.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BaseMessageRepository extends BaseModelRepository<Message> implements MessageRepository {
    @Override
    protected Class<Message> getModelClass() {
        return Message.class;
    }

    @Override
    public List<Message> getMessagesByChatId(Long id, int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> query = builder.createQuery(getModelClass());
        Root<Message> root = query.from(getModelClass());
        Predicate isChat = builder.equal(root.get("chat").get("id"), id);
        query.select(root);
        query.where(isChat);
        query.orderBy(builder.desc(root.get("date")));
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }
}
