package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.Selection;
import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SelectionRepository extends JpaRepository<Selection, Long> {

    Optional<Selection> findSelectionByUser_IdAndCat_Id(Long userId, Long catId);
    List<Selection> findSelectionsByUserAndDflagFalse(User user);
    List<Selection> findSelectionsByCatAndDflagFalse(Cat cat);

}
