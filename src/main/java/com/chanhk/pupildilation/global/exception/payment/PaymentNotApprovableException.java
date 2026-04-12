package com.chanhk.pupildilation.global.exception.payment;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class PaymentNotApprovableException extends BusinessException {
    public PaymentNotApprovableException() {
        super(ErrorCode.PAYMENT_NOT_APPROVABLE);
    }

}
