package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatRepository extends JpaRepository<Cat, Long> {
}
