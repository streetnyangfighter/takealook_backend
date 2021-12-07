package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatStatusRepository extends JpaRepository<CatStatus, Long> {
}
