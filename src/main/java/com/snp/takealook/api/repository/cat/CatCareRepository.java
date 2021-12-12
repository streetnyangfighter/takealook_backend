package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.cat.CatCare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CatCareRepository extends JpaRepository<CatCare, Long> {

    List<CatCare> findCatCaresByModifiedAtBefore(LocalDateTime dateTime);

    List<CatCare> findCatCaresBySelectionAndCreatedAtBetween(Selection selection, LocalDateTime dayStart, LocalDateTime dayEnd);
}
