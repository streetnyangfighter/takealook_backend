package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatLocationRepository extends JpaRepository<CatLocation, Long> {
}
