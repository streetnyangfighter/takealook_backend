package com.snp.takealook.repository.user;

import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    List<UserLocation> findUserLocationsByUser(User user);
}
