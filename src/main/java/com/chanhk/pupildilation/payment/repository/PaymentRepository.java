package com.chanhk.pupildilation.payment.repository;

import com.chanhk.pupildilation.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
