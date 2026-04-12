package com.chanhk.pupildilation.refund.repository;

import com.chanhk.pupildilation.refund.domain.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
