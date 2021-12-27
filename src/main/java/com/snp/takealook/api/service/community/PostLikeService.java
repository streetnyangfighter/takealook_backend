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
    @Transactional(rollbackFor = Exception.class)
    public void like(Long postId, PostLikeDTO.Like dto) {
        postLikeRepository.like(postId, dto.getUserId());
    }

    // 게시글 추천 취소
    @Transactional(rollbackFor = Exception.class)
    public void unlike(Long postId, Long userId) {
        postLikeRepository.unLike(postId, userId);
    }

}
