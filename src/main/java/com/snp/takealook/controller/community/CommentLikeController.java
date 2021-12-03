package com.snp.takealook.controller.community;

import com.snp.takealook.dto.community.CommentLikeDTO;
import com.snp.takealook.service.community.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 댓글 추천
    @PostMapping("/comment/{commentId}/like")
    public void like(@PathVariable Long commentId, @RequestBody CommentLikeDTO.Like dto) {
        commentLikeService.like(commentId, dto);
    }

    // 댓글 추천 취소
    @DeleteMapping("/comment/{commentId}/like")
    public void unlike(@PathVariable Long commentId, @RequestBody CommentLikeDTO.Like dto) {
        commentLikeService.unlike(commentId, dto);
    }
}
