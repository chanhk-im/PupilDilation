package com.chanhk.pupildilation.auth.dto.response;


import com.chanhk.pupildilation.global.common.Role;

public record SignupResponse(Long id, String email, String name, Role role) {
}
