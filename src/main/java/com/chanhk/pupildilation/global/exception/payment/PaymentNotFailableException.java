package com.chanhk.pupildilation.global.exception.payment;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class PaymentNotFailableException extends BusinessException {
    public PaymentNotFailableException() {
        super(ErrorCode.PAYMENT_NOT_FAILABLE);
    }

}
