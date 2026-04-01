package com.chanhk.pupildilation.user.repository;

import com.chanhk.pupildilation.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}