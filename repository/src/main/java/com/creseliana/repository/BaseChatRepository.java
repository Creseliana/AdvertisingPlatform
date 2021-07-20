package com.creseliana.repository;

import com.creseliana.model.Chat;
import com.creseliana.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.Optional;

@Repository
public class BaseChatRepository extends BaseModelRepository<Chat> implements ChatRepository {
//    private static final String QUERY =
//            "SELECT first.chat_id " +
//                    "FROM user_chat AS first, user_chat AS second " +
//                    "WHERE first.chat_id = second.chat_id " +
//                    "AND first.user_id = ? " +
//                    "AND second.user_id = ?";

    @Override
    protected Class<Chat> getModelClass() {
        return Chat.class;
    }

    public Optional<Long> getChatByTwoUsers(Long firstUserId, Long secondUserId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Chat> query = builder.createQuery(getModelClass());

        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<Chat> Chat_ = metamodel.entity(getModelClass());
//        EntityType<User> User_ = metamodel.entity(User.class);

        Root<Chat> chat = query.from(getModelClass());
        Root<User> user = query.from(User.class);
//        Join<Chat, User> userJoin = chat.join(Chat_.getSet("users", User.class));
        query.where(chat.join(Chat_.getSet("users", User.class)).in(firstUserId, secondUserId));
        query.where(user.get("chat.id"));


//        Query query = entityManager.createNativeQuery(QUERY, Long.class); //todo emergency solution
//        query.setParameter(1, firstUserId);
//        query.setParameter(2, secondUserId);
//        Object singleResult = query.getSingleResult();
        return Optional.empty(); //todo not finished
    }
}
