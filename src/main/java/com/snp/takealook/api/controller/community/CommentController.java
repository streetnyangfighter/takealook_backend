package com.snp.takealook.api.controller.community;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.community.CommentDTO;
import com.snp.takealook.api.dto.community.CommentLikeDTO;
import com.snp.takealook.api.service.community.CommentLikeService;
import com.snp.takealook.api.service.community.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentLikeService commentLikeService;

    // Comment -------------------------------------------------------------------------------
    // 댓글 작성
    @PostMapping("/post/{postId}/comment")
    public Long save(@PathVariable Long postId, @RequestBody CommentDTO.Create dto) {
        return commentService.save(postId, dto);
    }

    // 게시글 댓글 리스트 조회
    @GetMapping("post/{postId}/comment")
    public List<ResponseDTO.CommentResponse> findAllByPostId(@PathVariable Long postId) {
        return commentService.findAllPostId(postId);
    }

    // 댓글 수정
    @PatchMapping("/post/{postId}/comment/{commentId}")
    public Long update(@PathVariable Long commentId, @RequestBody CommentDTO.Update dto) {
        return commentService.update(commentId, dto);
    }

    // 댓글 삭제
    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public Long delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return commentId;
    }

    // Comment Like -------------------------------------------------------------------------
    // 댓글 추천
    @PostMapping("/post/{postId}/comment/{commentId}/like")
    public void like(@PathVariable Long commentId, @RequestBody CommentLikeDTO.Like dto) {
        commentLikeService.like(commentId, dto);
    }

    // 댓글 추천 취소
    @DeleteMapping("/post/{postId}/comment/{commentId}/like")
    public void unlike(@PathVariable Long commentId, @RequestBody CommentLikeDTO.Like dto) {
        commentLikeService.unlike(commentId, dto);
    }

    // 댓글별 추천 카운트
    @GetMapping("/post/{postId}/comment/{commentId}/like")
    public Long countLike(@PathVariable Long commentId) {
        return commentLikeService.countLike(commentId);
    }
}
