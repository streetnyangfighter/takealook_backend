package com.snp.takealook.api.repository.user;

import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByNickname(String nickname);
}
