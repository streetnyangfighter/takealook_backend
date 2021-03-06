package com.snp.takealook.api.controller.community;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.community.CommentDTO;
import com.snp.takealook.api.dto.community.CommentLikeDTO;
import com.snp.takealook.api.service.community.CommentLikeService;
import com.snp.takealook.api.service.community.CommentService;
import com.snp.takealook.api.service.user.NotificationService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final NotificationService notificationService;

    // Comment -------------------------------------------------------------------------------
    // 댓글 작성
    @PostMapping("/post/{postId}/comment")
    public Long save(@PathVariable Long postId, @RequestBody CommentDTO.Create dto) {
        Long commentId = commentService.save(postId, dto);
        notificationService.postSave(postId, dto.getWriterId(), (byte) 8);
        return commentId;
    }

    // 게시글 댓글 리스트 조회
    @GetMapping("/post/{postId}/comment")
    public List<ResponseDTO.CommentResponse> findAllByPostId(@PathVariable Long postId) {
        return commentService.findAllPostId(postId);
    }

    // 댓글 수정
    @PatchMapping("/post/{postId}/comment/{commentId}")
    public Long update(@PathVariable Long commentId, @AuthenticationPrincipal PrincipalDetails principal, @RequestBody CommentDTO.Update dto) {
        return commentService.update(commentId, principal.getUser().getId(), dto);
    }

    // 댓글 삭제
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public Long delete(@PathVariable Long commentId, @AuthenticationPrincipal PrincipalDetails principal) {
        commentService.delete(commentId, principal.getUser().getId());
        return commentId;
    }

    // Comment Like -------------------------------------------------------------------------
    // 댓글 추천
    @PostMapping("/post/{postId}/comment/{commentId}/like")
    public void like(@PathVariable Long commentId, @RequestBody CommentLikeDTO.Like dto) {
        commentLikeService.like(commentId, dto);
        notificationService.commentSave(commentId, dto.getUserId(), (byte) 10);
    }

    // 댓글 추천 취소
    @DeleteMapping("/post/{postId}/comment/{commentId}/like")
    public void unlike(@PathVariable Long commentId, @AuthenticationPrincipal PrincipalDetails principal) {
        commentLikeService.unlike(commentId, principal.getUser().getId());
    }

}
