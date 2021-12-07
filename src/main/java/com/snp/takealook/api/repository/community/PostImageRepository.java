package com.snp.takealook.api.repository.community;

import com.snp.takealook.api.domain.community.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
