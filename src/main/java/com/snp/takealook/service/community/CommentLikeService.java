package com.snp.takealook.service.community;

import com.snp.takealook.dto.community.CommentLikeDTO;
import com.snp.takealook.repository.community.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    // 댓글 추천
    @Transactional
    public void like(Long commentId, CommentLikeDTO.Like dto) {
        commentLikeRepository.like(commentId, dto.getUserId());
    }

    // 댓글 추천 취소
    @Transactional
    public void unlike(Long commentId, CommentLikeDTO.Like dto) {
        commentLikeRepository.unLike(commentId, dto.getUserId());
    }


}
