package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface CatLocationRepository extends JpaRepository<CatLocation, Long> {

    List<CatLocation> findCatLocationsByLatitudeBetweenAndLongitudeBetween(Double latitude_start, Double latitude_end, Double longitude_start, Double longitude_end);
}
