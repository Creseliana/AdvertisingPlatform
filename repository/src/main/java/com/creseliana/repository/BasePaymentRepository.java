package com.creseliana.repository;

import com.creseliana.model.Payment;
import org.springframework.stereotype.Repository;

@Repository
public class BasePaymentRepository extends BaseModelRepository<Payment> implements PaymentRepository {
    @Override
    protected Class<Payment> getModelClass() {
        return Payment.class;
    }
}
