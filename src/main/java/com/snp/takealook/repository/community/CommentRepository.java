package com.snp.takealook.repository.community;

import com.snp.takealook.domain.community.Comment;
import com.snp.takealook.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
}
