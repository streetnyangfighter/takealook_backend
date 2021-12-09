package com.snp.takealook.api.repository;

import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SelectionRepository extends JpaRepository<Selection, Long> {

    Optional<Selection> findSelectionByUserAndCat(User user, Cat cat);
    Optional<Selection> findSelectionByUser_IdAndCat_Id(Long userId, Long catId);
    List<Selection> findSelectionsByUser(User user);
    List<Selection> findSelectionsByCat(Cat cat);
}
