package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.CatImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatImageRepository extends JpaRepository<CatImage, Long> {

    CatImage findByPath(String path);
}
