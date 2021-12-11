package com.snp.takealook.api.repository.user;

import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUsersByModifiedAtBeforeAndDflagTrue(LocalDateTime dateTime);
    User findByLoginId(String loginId);
    User findByNickname(String nickname);

    Optional<User> findByLoginIdAndLoginType(String loginId, String registrationId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from User u where u.dflag = true and u.modified_at <= :limitDate", nativeQuery = true)
    int hardDelete(@Param("limitDate") LocalDateTime limitDate);
}
