package com.chanhk.pupildilation.user.domain;

import com.chanhk.pupildilation.global.BaseEntity;
import com.chanhk.pupildilation.global.common.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public static User of(String email, String password, String name) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(Role.STUDENT)
                .build();
    }

    public static User of(String email, String password, String name, Role role) {
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .role(role)
                .build();
    }
}
