package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatCare;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatCareRepository extends JpaRepository<CatCare, Long> {
}
