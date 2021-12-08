package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatMatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatMatchRepository extends JpaRepository<CatMatch, Long> {

    List<CatMatch> findCatMatchesByProposer_Id(Long proposerId);
    List<CatMatch> findCatMatchesByAccepter_Id(Long accepterId);
}
