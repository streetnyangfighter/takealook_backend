package com.snp.takealook.repository.community;

import com.snp.takealook.domain.community.Board;
import com.snp.takealook.domain.community.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBoard(Board board);
}
