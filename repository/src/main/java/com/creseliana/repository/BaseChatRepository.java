package com.creseliana.repository;

import com.creseliana.model.Chat;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class BaseChatRepository extends BaseModelRepository<Chat> implements ChatRepository {

    @Override
    protected Class<Chat> getModelClass() {
        return Chat.class;
    }

    @Override
    public Optional<Chat> getChatByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chat> query = builder.createQuery(getModelClass());
        Root<Chat> root = query.from(getModelClass());
        Predicate firstIdMatchesFirstUser = builder.equal(root.get("first_user_id"), firstUserId);
        Predicate secondIdMatchesSecondUser = builder.equal(root.get("second_user_id"), secondUserId);
        Predicate firstIdMatchesSecondUser = builder.equal(root.get("first_user_id"), secondUserId);
        Predicate secondIdMatchesFirstUser = builder.equal(root.get("second_user_id"), firstUserId);
        Predicate isFirstAndSecond = builder.and(firstIdMatchesFirstUser, secondIdMatchesSecondUser);
        Predicate isSecondAndFirst = builder.and(firstIdMatchesSecondUser, secondIdMatchesFirstUser);
        query.select(root);
        query.where(builder.or(isFirstAndSecond, isSecondAndFirst));
        List<Chat> chats = entityManager.createQuery(query).getResultList();
        if (chats.size() > 1) {
            throw new RuntimeException();
            //todo throw exception
        }
        return chats.stream().findFirst();
    }
}
