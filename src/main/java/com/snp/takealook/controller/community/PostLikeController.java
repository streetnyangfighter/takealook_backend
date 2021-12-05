package com.snp.takealook.controller.community;

import com.snp.takealook.dto.community.PostLikeDTO;
import com.snp.takealook.service.community.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 게시글 추천
    @PostMapping("/post/{postId}/like")
    public void like(@PathVariable Long postId, @RequestBody PostLikeDTO.Like dto) {
        postLikeService.like(postId, dto);
    }

    // 게시글 추천 취소
    @DeleteMapping("/post/{postId}/like")
    public void unlike(@PathVariable Long postId, @RequestBody PostLikeDTO.Like dto) {
        postLikeService.unlike(postId, dto);
    }

    // 게시글별 추천 카운트
    @GetMapping("/post/{postId}/like")
    public Long countLike(@PathVariable Long postId) {
        return postLikeService.countLike(postId);
    }

}
