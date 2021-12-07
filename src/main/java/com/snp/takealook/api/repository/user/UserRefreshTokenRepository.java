package com.snp.takealook.api.repository.user;

import com.snp.takealook.api.domain.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByLoginId(String loginId);
    UserRefreshToken findByLoginIdAndRefreshToken(String loginId, String refreshToken);
}
