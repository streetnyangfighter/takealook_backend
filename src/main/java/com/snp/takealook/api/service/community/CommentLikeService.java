package com.snp.takealook.api.service.community;

import com.snp.takealook.api.dto.community.CommentLikeDTO;
import com.snp.takealook.api.repository.community.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    // 댓글 추천
    @Transactional(rollbackFor = {RuntimeException.class})
    public void like(Long commentId, CommentLikeDTO.Like dto) {
        commentLikeRepository.like(commentId, dto.getUserId());
    }

    // 댓글 추천 취소
    @Transactional(rollbackFor = {RuntimeException.class})
    public void unlike(Long commentId, CommentLikeDTO.Like dto) {
        commentLikeRepository.unLike(commentId, dto.getUserId());
    }

    // 댓글별 추천 카운트
    @Transactional(readOnly = true)
    public Long countLike(Long commentId) {
        return commentLikeRepository.findAllByCommentId(commentId).stream().count();
    }
}
