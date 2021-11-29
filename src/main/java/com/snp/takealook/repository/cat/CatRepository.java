package com.snp.takealook.repository.cat;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Long> {
    List<Cat> findCatsByUser(User user);
}
