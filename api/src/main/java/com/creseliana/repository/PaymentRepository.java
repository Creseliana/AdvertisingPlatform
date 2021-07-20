package com.creseliana.repository;

import com.creseliana.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends ModelRepository<Payment, Long> {

    Optional<Payment> getCurrentPaymentByAdId(Long adId);
}
