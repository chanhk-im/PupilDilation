package com.chanhk.pupildilation.global.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Constants {
    public static final String EMAIL_REGX_STRING = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$";
    public static final String EMAIL_VALID_MESSAGE = "이메일 주소 양식을 확인해주세요";
}
