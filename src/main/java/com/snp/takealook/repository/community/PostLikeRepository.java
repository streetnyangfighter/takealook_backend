package com.snp.takealook.repository.community;

import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.community.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    @Modifying
    @Query(value = "INSERT INTO post_like(post_id, user_id, created_at, modified_at) VALUES(:postId, :userId, now(), now())", nativeQuery = true)
    void like(long postId, long userId);

    @Modifying
    @Query(value = "DELETE FROM post_like WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void unLike(long postId, long userId);

    List<PostLike> findAllByPostId(Long postId);
}
