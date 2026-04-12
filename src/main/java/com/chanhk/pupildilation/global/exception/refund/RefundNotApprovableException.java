package com.chanhk.pupildilation.global.exception.refund;

import com.chanhk.pupildilation.global.exception.BusinessException;
import com.chanhk.pupildilation.global.exception.ErrorCode;

public class RefundNotApprovableException extends BusinessException {
    public RefundNotApprovableException() {
        super(ErrorCode.REFUND_NOT_APPROVABLE);
    }

}
