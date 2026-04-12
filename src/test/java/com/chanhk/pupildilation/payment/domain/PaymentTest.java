package com.chanhk.pupildilation.payment.domain;

import com.chanhk.pupildilation.global.common.PaymentStatus;
import com.chanhk.pupildilation.global.exception.payment.PaymentNotApprovableException;
import com.chanhk.pupildilation.global.exception.payment.PaymentNotFailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Payment 엔터티 테스트")
class PaymentTest {

    @Test
    void 대기중인_결제는_승인_성공한다() {
        Payment payment = Payment.builder()
                .totalAmount(10000)
                .orderId("order-001")
                .build(); // status 기본값 PENDING

        LocalDateTime approvedAt = LocalDateTime.now();
        payment.approve("payment-key-001", approvedAt);

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.DONE);
        assertThat(payment.getPaymentKey()).isEqualTo("payment-key-001");
        assertThat(payment.getApprovedAt()).isEqualTo(approvedAt);
    }

    @Test
    void 대기중이_아닌_결제는_승인_실패한다() {
        Payment payment = Payment.builder()
                .totalAmount(10000)
                .orderId("order-001")
                .status(PaymentStatus.DONE)
                .build();

        assertThatThrownBy(() -> payment.approve("payment-key-001", LocalDateTime.now()))
                .isInstanceOf(PaymentNotApprovableException.class);
    }

    @Test
    void 대기중인_결제는_실패_처리_성공한다() {
        Payment payment = Payment.builder()
                .totalAmount(10000)
                .orderId("order-001")
                .build(); // status 기본값 PENDING

        payment.fail();

        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    void 대기중이_아닌_결제는_실패_처리_실패한다() {
        Payment payment = Payment.builder()
                .totalAmount(10000)
                .orderId("order-001")
                .status(PaymentStatus.DONE)
                .build();

        assertThatThrownBy(payment::fail)
                .isInstanceOf(PaymentNotFailableException.class);
    }
}