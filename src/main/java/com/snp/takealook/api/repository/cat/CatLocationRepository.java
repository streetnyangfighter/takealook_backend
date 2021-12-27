package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CatLocationRepository extends JpaRepository<CatLocation, Long> {

    @Query(value = "SELECT *, " +
            "(6371*acos(cos(radians(:catLatitute))*cos(radians(latitude))*cos(radians(longitude)" +
            "-radians(:catLongitude))+sin(radians(:catLatitute))*sin(radians(latitude))))" +
            "AS distance FROM cat_location HAVING distance <= 2",
            nativeQuery = true)
    List<CatLocation> findNearCatLocations(@Param("catLatitute")Double latitude, @Param("catLongitude")Double longitude);

}
