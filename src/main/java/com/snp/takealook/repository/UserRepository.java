package com.snp.takealook.repository;

import com.snp.takealook.domain.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);
    Optional<User> findByLoginIdAndLoginType(String loginId, String loginType);
}
