package com.creseliana.repository;

import com.creseliana.model.Advertisement;
import com.creseliana.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
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
    public List<Advertisement> getPayedAdsOrderByDate(int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> query = builder.createQuery(getModelClass());
        Root<Advertisement> root = query.from(getModelClass());
        Join<Advertisement, Payment> paymentJoin = root.join("ad"); //todo check is it working
        query.select(root);
        Predicate endDateGreaterThanNow = builder.greaterThan(paymentJoin.get("endDate"), LocalDateTime.now());
        query.where(endDateGreaterThanNow);
        query.orderBy(builder.desc(root.get("creationDate")));
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
        //Root<Payment> root = query.from(Payment.class);
        //Path<Advertisement> path = root.get("ad");
    }
}
