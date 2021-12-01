package com.snp.takealook.repository.community;

import com.snp.takealook.domain.community.Comment;
import com.snp.takealook.domain.community.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    void deleteLikesByComment(Comment comment);

    @Modifying
    @Query(value = "INSERT INTO comment_like(comment_id, user_id) VALUES(:commentId, :userId)", nativeQuery = true)
    void like(long commentId, long userId);

    @Modifying
    @Query(value = "DELETE FROM comment_like WHERE comment_id = :commentId AND user_id = :userId", nativeQuery = true)
    void unLike(long commentId, long userId);
}
