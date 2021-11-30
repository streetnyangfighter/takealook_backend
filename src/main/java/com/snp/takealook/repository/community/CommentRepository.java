package com.snp.takealook.repository.community;

import com.snp.takealook.domain.community.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
