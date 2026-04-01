package com.chanhk.pupildilation.payment.domain;

import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.global.common.PaymentStatus;
import com.chanhk.pupildilation.global.exception.payment.PaymentNotApprovableException;
import com.chanhk.pupildilation.global.exception.payment.PaymentNotFailableException;
import com.chanhk.pupildilation.reservation.domain.Reservation;
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
@Table(name = "payments")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Payment extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", unique = true)
    private Reservation reservation;

    @Column(nullable = false, length = 100, unique = true)
    private String orderId;

    @Column(length = 200)
    private String paymentKey;

    @Column(length = 50)
    private String mid;

    @Column(length = 50)
    private String method;

    @Column(nullable = false)
    private Integer totalAmount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime requestedAt = LocalDateTime.now();

    private LocalDateTime approvedAt;

    public void approve(String paymentKey, LocalDateTime approvedAt) {
        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentNotApprovableException();
        }
        this.paymentKey = paymentKey;
        this.approvedAt = approvedAt;
        this.status = PaymentStatus.DONE;
    }

    public void fail() {
        if (this.status != PaymentStatus.PENDING) {
            throw new PaymentNotFailableException();
        }
        this.status = PaymentStatus.FAILED;
    }
}
