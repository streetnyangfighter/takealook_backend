package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {

    List<Cat> findCatsByUser(User user);

    List<Cat> findCatsByModifiedAtBeforeAndDflagTrue(LocalDateTime dateTime);

}
