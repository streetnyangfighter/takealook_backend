package com.snp.takealook.api.controller.community;

import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.community.PostImage;
import com.snp.takealook.api.dto.community.PostDTO;
import com.snp.takealook.api.dto.community.PostLikeDTO;
import com.snp.takealook.api.service.community.PostImageService;
import com.snp.takealook.api.service.community.PostLikeService;
import com.snp.takealook.api.service.community.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final PostImageService postImageService;

    // Post -------------------------------------------------------------------------------
    // 게시글 작성
    @PostMapping("/post")
    public Long save(@RequestBody PostDTO.Create dto) {
        return postService.save(dto);
    }

    // 게시글 리스트 조회
    @GetMapping("/posts/{boardId}")
    public List<PostDTO.Get> findAllByBoardId(@PathVariable Long boardId) {
        return postService.findAllByBoardId(boardId);
    }

    // 게시글 상세 조회
    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @PutMapping("/post/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostDTO.Update dto) {
        return postService.update(id, dto);
    }

    // 게시글 삭제
    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }

    // Post Thumbnail -------------------------------------------------------------------
    // 게시글 썸네일 추가
    @PostMapping("/post/{id}/thumbnail")
    public Long save(@PathVariable Long id, @RequestPart(value = "files") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return postImageService.save(id, file);
    }

    // 게시글 썸네일 조회
    @GetMapping("/post/{id}/thumbnail")
    public PostImage getThumbnail(@PathVariable Long id) {
        return postImageService.getTumbnail(id);
    }

    // Post Like ------------------------------------------------------------------------
    // 게시글 추천
    @PostMapping("/post/{id}/like")
    public void like(@PathVariable Long id, @RequestBody PostLikeDTO.Like dto) {
        postLikeService.like(id, dto);
    }

    // 게시글 추천 취소
    @DeleteMapping("/post/{id}/like")
    public void unlike(@PathVariable Long id, @RequestBody PostLikeDTO.Like dto) {
        postLikeService.unlike(id, dto);
    }

    // 게시글별 추천 카운트
    @GetMapping("/post/{id}/like")
    public Long countLike(@PathVariable Long id) {
        return postLikeService.countLike(id);
    }
}
