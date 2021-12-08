package com.snp.takealook.api.repository.user;

import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByModifiedAtBeforeAndDflagTrue(LocalDateTime dateTime);
    User findByLoginId(String loginId);
    User findByNickname(String nickname);

    Optional<User> findByLoginIdAndLoginType(String loginId, String registrationId);
}
