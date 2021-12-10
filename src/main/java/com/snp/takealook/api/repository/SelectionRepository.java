package com.snp.takealook.api.repository;

import com.snp.takealook.api.domain.Selection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SelectionRepository extends JpaRepository<Selection, Long> {

    Optional<Selection> findSelectionByUser_IdAndCat_Id(Long userId, Long catId);

}
