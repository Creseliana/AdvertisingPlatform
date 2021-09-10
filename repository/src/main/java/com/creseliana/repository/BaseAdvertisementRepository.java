package com.creseliana.repository;

import com.creseliana.model.Advertisement;
import com.creseliana.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseAdvertisementRepository extends BaseModelRepository<Advertisement> implements AdvertisementRepository {
    @Override
    protected Class<Advertisement> getModelClass() {
        return Advertisement.class;
    }

    @Override
    public List<Advertisement> getAdsByClosedAndAuthorId(boolean isClosed, Long id, int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> query = builder.createQuery(getModelClass());
        Root<Advertisement> root = query.from(getModelClass());
        query.select(root);
        Predicate isThisUser = builder.equal(root.get("author").get("id"), id);
        Predicate notDeleted = builder.equal(root.get("isDeleted"), false);
        Predicate closed = builder.equal(root.get("isClosed"), isClosed);
        query.where(builder.and(isThisUser, notDeleted, closed));
        query.orderBy(builder.desc(root.get("creationDate")));
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }

    @Override
    public List<Advertisement> getAllAdsOrdered(String categoryName, int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> query = builder.createQuery(getModelClass());
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<Advertisement> adMetamodel = metamodel.entity(Advertisement.class);
        Root<Advertisement> advertisementRoot = query.from(Advertisement.class);
        Join<Advertisement, Payment> paymentJoin = advertisementRoot.join(adMetamodel.getList("payments", Payment.class), JoinType.LEFT);
        query.select(advertisementRoot);
        if (categoryName != null) {
            query.where(builder.equal(advertisementRoot.get("category").get("name"), categoryName));
        }
        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(paymentJoin.get("endDate")));
        orders.add(builder.desc(advertisementRoot.get("author").get("rating")));
        orders.add(builder.desc(paymentJoin.get("startDate")));
        query.orderBy(orders);
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }
}
