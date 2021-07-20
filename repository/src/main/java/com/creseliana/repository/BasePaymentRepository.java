package com.creseliana.repository;

import com.creseliana.model.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class BasePaymentRepository extends BaseModelRepository<Payment> implements PaymentRepository {
    @Override
    protected Class<Payment> getModelClass() {
        return Payment.class;
    }

    @Override
    public Optional<Payment> getCurrentPaymentByAdId(Long adId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> query = builder.createQuery(getModelClass());
        Root<Payment> root = query.from(getModelClass());
        query.select(root);
        Predicate isId = builder.equal(root.get("ad_id"), adId);
        Predicate isValidDate = builder.greaterThan(root.get("end_date"), LocalDateTime.now());
        query.where(builder.and(isId, isValidDate));
        List<Payment> payments = entityManager.createQuery(query).getResultList();
        if (payments.size() > 1) {
            throw new RuntimeException();
            //todo throw exception
        }
        return payments.stream().findFirst();
    }
}
