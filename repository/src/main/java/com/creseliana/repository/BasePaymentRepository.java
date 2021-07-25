package com.creseliana.repository;

import com.creseliana.model.Payment;
import com.creseliana.repository.exception.MultiplePaymentMatchingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Repository
public class BasePaymentRepository extends BaseModelRepository<Payment> implements PaymentRepository {
    private static final String MSG_MULTIPLE_PAYMENTS = "There are more than one current payment matching ad id '%s'";

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
        Predicate isId = builder.equal(root.get("ad").get("id"), adId);
        Predicate isValidDate = builder.greaterThan(root.get("endDate"), LocalDateTime.now());
        query.where(builder.and(isId, isValidDate));
        List<Payment> payments = entityManager.createQuery(query).getResultList();
        if (payments.size() > 1) {
            String msg = String.format(MSG_MULTIPLE_PAYMENTS, adId);
            log.warn(msg);
            throw new MultiplePaymentMatchingException(msg); //todo try to merge payments into one?
        }
        return payments.stream().findFirst();
    }

    @Override
    public long countAllByEndDateAfterNow() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Payment> root = query.from(getModelClass());
        Predicate endDateGreaterThanNow = builder.greaterThan(root.get("endDate"), LocalDateTime.now());
        query.select(builder.count(root));
        query.where(endDateGreaterThanNow);
        return entityManager.createQuery(query).getSingleResult();
    }
}
