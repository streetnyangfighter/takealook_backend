package com.snp.takealook.controller.community;

import com.snp.takealook.dto.community.CommentDTO;
import com.snp.takealook.service.community.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/post/{postId}/comment")
    public Long save(@PathVariable Long postId, @RequestBody CommentDTO.Create dto) {
        return commentService.save(postId, dto);
    }

    // 게시글 댓글 리스트 조회
    @GetMapping("post/{postId}/comment")
    public List<CommentDTO.Get> findAllByPostId(@PathVariable Long postId) {
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
}
