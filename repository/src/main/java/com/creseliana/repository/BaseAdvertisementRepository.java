package com.creseliana.repository;

import com.creseliana.model.Advertisement;
import com.creseliana.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
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
    public List<Advertisement> getAllAdsOrdered(int start, int amount) { //todo not working
        LocalDateTime currentDateTime = LocalDateTime.now();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> query = builder.createQuery(getModelClass());
        Root<Advertisement> advertisementRoot = query.from(Advertisement.class);
        Root<Payment> paymentRoot = query.from(Payment.class);
        Join<Payment, Advertisement> join = paymentRoot.join("ad");
        query.select(advertisementRoot);

        List<Order> orders = new ArrayList<>();
        orders.add(builder.desc(builder.greaterThan(join.get("endDate"), currentDateTime)));
        orders.add(builder.desc(advertisementRoot.get("author").get("rating")));
        orders.add(builder.desc(join.get("startDate")));
        query.orderBy(orders);
/*        Root<Advertisement> root = query.from(getModelClass());

//        Subquery<Advertisement> adSubQuery = query.subquery(getModelClass());
//        Root<Payment> subRoot = adSubQuery.from(Payment.class);
//        adSubQuery.select(subRoot.get("ad"));
//        Predicate startDateLessThanNow = builder.lessThan(subRoot.get("startDate"), currentDateTime);
//        Predicate endDateGreaterThanNow = builder.greaterThan(subRoot.get("endDate"), currentDateTime);
//        adSubQuery.where(endDateGreaterThanNow);

        List<Order> orders = new ArrayList<>();
//        orders.add(builder.desc(adSubQuery));
//        orders.add(builder.desc(builder.greaterThan(root.get("payment").get("endDate"), currentDateTime)));
//        orders.add(builder.desc(root.get("payment").get("startDate")));
        orders.add(builder.desc(root.get("author").get("rating")));
        orders.add(builder.desc(root.get("creationDate")));

//        query.groupBy(subRoot.get("endDate"));
        query.orderBy(orders);*/
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }
}
