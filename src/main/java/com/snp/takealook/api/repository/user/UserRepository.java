package com.snp.takealook.api.repository.user;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.oauth.entity.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);
    User findByNickname(String nickname);
    Optional<User> findByLoginIdAndProviderType(String loginId, ProviderType providerType);
}
