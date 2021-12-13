package com.snp.takealook.api.repository.cat;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.MainImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainImageRepository extends JpaRepository<MainImage, Long> {

    MainImage findMainImageByCat(Cat cat);
}
