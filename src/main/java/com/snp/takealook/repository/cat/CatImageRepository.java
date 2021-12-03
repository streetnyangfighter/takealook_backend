package com.snp.takealook.repository.cat;

import com.snp.takealook.domain.cat.CatImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatImageRepository extends JpaRepository<CatImage, Long> {
}
