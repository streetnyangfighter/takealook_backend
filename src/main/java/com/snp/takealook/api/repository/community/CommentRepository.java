package com.snp.takealook.api.repository.community;

import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedAtAsc(Post post);
    Optional<Comment> findByIdAndWriter(Long commentId, Long userId);
}
