package com.snp.takealook.api.service.community;

import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.dto.community.PostLikeDTO;
import com.snp.takealook.api.repository.community.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostLikeService extends BaseTimeEntity {

    private final PostLikeRepository postLikeRepository;
    
    // 게시글 추천
    @Transactional
    public void like(Long postId, PostLikeDTO.Like dto) {
        postLikeRepository.like(postId, dto.getUserId());
    }

    // 게시글 추천 취소
    @Transactional
    public void unlike(Long postId, PostLikeDTO.Like dto) {
        postLikeRepository.unLike(postId, dto.getUserId());
    }

    // 게시글별 추천 카운트
    @Transactional(readOnly = true)
    public Long countLike(Long postId) {
        return postLikeRepository.findAllByPostId(postId).stream().count();
    }
}
