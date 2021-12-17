package com.snp.takealook.api.repository.community;

import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedAtAsc(Post post);
}
