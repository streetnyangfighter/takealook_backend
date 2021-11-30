package com.snp.takealook.repository.cat;

import com.snp.takealook.domain.cat.CatLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatLocationRepository extends JpaRepository<CatLocation, Long> {
}
