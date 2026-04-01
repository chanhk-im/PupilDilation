package com.chanhk.pupildilation.global.exception.refund;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class RefundNotFailableException extends BusinessException {
    public RefundNotFailableException() {
        super(ErrorCode.REFUND_NOT_FAILABLE);
    }

}
