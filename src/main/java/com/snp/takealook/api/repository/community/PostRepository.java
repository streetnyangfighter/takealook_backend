package com.snp.takealook.api.repository.community;

import com.snp.takealook.api.domain.community.Board;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoardOrderByCreatedAtDesc(Board board);
    List<Post> findAllByWriter(User user);
    Optional<Post> findByIdAndWriter(Long postId, Long userId);
}
