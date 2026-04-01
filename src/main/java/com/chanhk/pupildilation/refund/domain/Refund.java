package com.chanhk.pupildilation.refund.domain;

import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.global.common.RefundStatus;
import com.chanhk.pupildilation.global.exception.refund.RefundNotApprovableException;
import com.chanhk.pupildilation.global.exception.refund.RefundNotFailableException;
import com.chanhk.pupildilation.payment.domain.Payment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refunds")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Refund extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    @Column(nullable = false)
    private Integer refundAmount;

    @Column(length = 255)
    private String reason;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RefundStatus status = RefundStatus.PENDING;

    private LocalDateTime refundedAt;

    public void approve() {
        if (this.status != RefundStatus.PENDING) {
            throw new RefundNotApprovableException();
        }

        this.status = RefundStatus.COMPLETED;
        this.refundedAt = LocalDateTime.now();
    }

    public void fail() {
        if (this.status != RefundStatus.PENDING) {
            throw new RefundNotFailableException();
        }

        this.status = RefundStatus.FAILED;
    }
}
