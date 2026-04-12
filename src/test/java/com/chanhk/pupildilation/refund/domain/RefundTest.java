package com.chanhk.pupildilation.refund.domain;

import com.chanhk.pupildilation.global.common.RefundStatus;
import com.chanhk.pupildilation.global.exception.refund.RefundNotApprovableException;
import com.chanhk.pupildilation.global.exception.refund.RefundNotFailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Refund 엔터티 테스트")
class RefundTest {

    @Test
    void 대기중인_환불은_승인_성공한다() {
        Refund refund = Refund.builder()
                .refundAmount(10000)
                .build(); // status 기본값 PENDING

        refund.approve();

        assertThat(refund.getStatus()).isEqualTo(RefundStatus.COMPLETED);
        assertThat(refund.getRefundedAt()).isNotNull();
    }

    @Test
    void 대기중이_아닌_환불은_승인_실패한다() {
        Refund refund = Refund.builder()
                .refundAmount(10000)
                .status(RefundStatus.COMPLETED)
                .build();

        assertThatThrownBy(refund::approve)
                .isInstanceOf(RefundNotApprovableException.class);
    }

    @Test
    void 대기중인_환불은_실패_처리_성공한다() {
        Refund refund = Refund.builder()
                .refundAmount(10000)
                .build(); // status 기본값 PENDING

        refund.fail();

        assertThat(refund.getStatus()).isEqualTo(RefundStatus.FAILED);
    }

    @Test
    void 대기중이_아닌_환불은_실패_처리_실패한다() {
        Refund refund = Refund.builder()
                .refundAmount(10000)
                .status(RefundStatus.COMPLETED)
                .build();

        assertThatThrownBy(refund::fail)
                .isInstanceOf(RefundNotFailableException.class);
    }
}