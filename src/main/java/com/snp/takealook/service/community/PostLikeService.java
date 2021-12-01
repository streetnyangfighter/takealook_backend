package com.snp.takealook.service.community;

import com.snp.takealook.dto.community.PostLikeDTO;
import com.snp.takealook.repository.community.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    
    // 게시글 추천
    @Transactional
    public void like(Long postId, PostLikeDTO.Like dto) {
        postLikeRepository.like(postId, dto.getUserId());
    }

    // 게시글 삭제
    @Transactional
    public void unlike(Long postId, PostLikeDTO.Like dto) {
        postLikeRepository.unLike(postId, dto.getUserId());
    }
}
