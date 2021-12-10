package com.snp.takealook.api.controller.community;

import com.snp.takealook.api.domain.community.PostImage;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.community.PostDTO;
import com.snp.takealook.api.dto.community.PostLikeDTO;
import com.snp.takealook.api.service.community.PostImageService;
import com.snp.takealook.api.service.community.PostLikeService;
import com.snp.takealook.api.service.community.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    // 게시글 작성 + 썸네일 추가
    @PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@RequestPart(value = "postText") PostDTO.Create dto,
                     @RequestPart(value = "postImage") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Long postId = postService.save(dto);
        postImageService.save(postId, file);

        return postId;
    }

    // 게시글 리스트 조회
    @GetMapping("/posts/{boardId}")
    public List<ResponseDTO.PostResponse> findAllByBoardId(@PathVariable Long boardId) {
        return postService.findAllByBoardId(boardId);
    }

    // 게시글 상세 조회
    @GetMapping("/post/{id}")
    public ResponseDTO.PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @PostMapping("/post/{id}")
    public Long update(@PathVariable Long id,
                       @RequestPart(value = "postText") PostDTO.Update dto,
                       @RequestPart(value = "postImage") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Long postId = postService.update(id, dto);
        System.out.println(postId);
        postImageService.save(postId, file);

        return postId;
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
