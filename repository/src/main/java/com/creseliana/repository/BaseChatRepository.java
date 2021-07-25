package com.creseliana.repository;

import com.creseliana.model.Chat;
import com.creseliana.repository.exception.MultipleChatMatchingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class BaseChatRepository extends BaseModelRepository<Chat> implements ChatRepository {
    private static final String MSG_MULTIPLE_CHATS = "There are more than one chat matching user ids '%s' and '%s'";

    @Override
    protected Class<Chat> getModelClass() {
        return Chat.class;
    }

    @Override
    public Optional<Chat> getChatByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chat> query = builder.createQuery(getModelClass());
        Root<Chat> root = query.from(getModelClass());
        Predicate firstIdMatchesFirstUser = builder.equal(root.get("firstUser").get("id"), firstUserId);
        Predicate secondIdMatchesSecondUser = builder.equal(root.get("secondUser").get("id"), secondUserId);
        Predicate firstIdMatchesSecondUser = builder.equal(root.get("firstUser").get("id"), secondUserId);
        Predicate secondIdMatchesFirstUser = builder.equal(root.get("secondUser").get("id"), firstUserId);
        Predicate isFirstAndSecond = builder.and(firstIdMatchesFirstUser, secondIdMatchesSecondUser);
        Predicate isSecondAndFirst = builder.and(firstIdMatchesSecondUser, secondIdMatchesFirstUser);
        query.select(root);
        query.where(builder.or(isFirstAndSecond, isSecondAndFirst));
        List<Chat> chats = entityManager.createQuery(query).getResultList();
        if (chats.size() > 1) {
            String msg = String.format(MSG_MULTIPLE_CHATS, firstUserId, secondUserId);
            log.warn(msg);
            throw new MultipleChatMatchingException(msg); //todo try to merge chats into one?
        }
        return chats.stream().findFirst();
    }

    @Override
    public List<Chat> getChatsByUserId(Long id, int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chat> query = builder.createQuery(getModelClass());
        Root<Chat> root = query.from(getModelClass());
        Predicate isFirstUser = builder.equal(root.get("firstUser").get("id"), id);
        Predicate isSecondUser = builder.equal(root.get("secondUser").get("id"), id);
        query.select(root);
        query.where(builder.or(isFirstUser, isSecondUser));
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }
}
