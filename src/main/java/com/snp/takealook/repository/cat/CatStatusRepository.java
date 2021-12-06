package com.snp.takealook.repository.cat;

import com.snp.takealook.domain.cat.CatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatStatusRepository extends JpaRepository<CatStatus, Long> {
}
