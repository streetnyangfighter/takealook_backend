package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CatLocationRepository extends JpaRepository<CatLocation, Long> {

    List<CatLocation> findCatLocationsByModifiedAtBefore(LocalDateTime dateTime);
}
