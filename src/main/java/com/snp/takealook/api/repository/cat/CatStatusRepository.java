package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CatStatusRepository extends JpaRepository<CatStatus, Long> {

    List<CatStatus> findCatStatusByModifiedAtBefore(LocalDateTime dateTime);
}
