package com.creseliana.repository;

import com.creseliana.model.Advertisement;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class BaseAdvertisementRepository extends BaseModelRepository<Advertisement> implements AdvertisementRepository {
    @Override
    protected Class<Advertisement> getModelClass() {
        return Advertisement.class;
    }

    @Override
    public List<Advertisement> getCompletedAdsByUserId(Long id, int start, int amount) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Advertisement> query = builder.createQuery(getModelClass());
        Root<Advertisement> root = query.from(getModelClass());
        query.select(root);
        Predicate notDeleted = builder.equal(root.get("is_deleted"), false);
        Predicate isClosed = builder.equal(root.get("is_closed"), true);
        query.where(builder.and(notDeleted, isClosed));
        query.orderBy(builder.desc(root.get("creation_date")));
        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(amount)
                .getResultList();
    }
}
