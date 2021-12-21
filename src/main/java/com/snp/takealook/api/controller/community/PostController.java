package com.snp.takealook.api.controller.community;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.community.PostDTO;
import com.snp.takealook.api.dto.community.PostLikeDTO;
import com.snp.takealook.api.service.S3Uploader;
import com.snp.takealook.api.service.community.PostLikeService;
import com.snp.takealook.api.service.community.PostService;
import com.snp.takealook.api.service.user.NotificationService;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostLikeService postLikeService;
    private final NotificationService notificationService;
    private final S3Uploader s3Uploader;

    // Post -------------------------------------------------------------------------------
    // 게시글 작성 + 썸네일 추가
    @PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@RequestPart(value = "postText") PostDTO.Create dto,
                     @RequestPart(value = "postImage") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String imgUrl = s3Uploader.upload(file, "static");
        Long postId = postService.save(dto, imgUrl);

        return postId;
    }

    // 게시글 작성 시, quill 사진 등록
    @PostMapping(value = "/post/photo", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String savePhoto(@RequestPart(value= "file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return s3Uploader.upload(file, "static");
    }

    // 게시글 리스트 조회 (게시판 구분 x)
    @GetMapping("/posts")
    public List<ResponseDTO.PostResponse> findAllPosts() {
        return postService.findAllPosts();
    }

    // 게시글 리스트 조회 (게시판별)
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
    @PostMapping("/post/{postId}")
    public Long update(@PathVariable Long postId,
                       @AuthenticationPrincipal PrincipalDetails principal,
                       @RequestPart(value = "postText") PostDTO.Update dto,
                       @RequestPart(value = "postImage", required = false) MultipartFile file) throws IOException, NoSuchAlgorithmException {

        User user = principal.getUser();

        if (file != null) {
            String imgUrl = s3Uploader.upload(file, "static");
            return postService.update(postId, user.getId(), dto, imgUrl);
        } else {
            return postService.update(postId, user.getId(), dto, null);
        }

    }

    // 게시글 삭제
    @DeleteMapping("/post/{postId}")
    public Long delete(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetails principal) {

        User user = principal.getUser();

        postService.delete(postId, user.getId());
        return postId;
    }

    // 게시글 제목 검색
    @GetMapping("/post/search")
    public List<ResponseDTO.PostResponse> search(@RequestParam("search") String search) {
        return postService.searchTitle(search);
    }

    // Post Like ------------------------------------------------------------------------
    // 게시글 추천
    @PostMapping("/post/{id}/like")
    public void like(@PathVariable Long id, @RequestBody PostLikeDTO.Like dto) {
        postLikeService.like(id, dto);
        notificationService.postSave(id, dto.getUserId(), (byte) 9);
    }

    // 게시글 추천 취소
    @DeleteMapping("/post/{postId}/like")
    public void unlike(@PathVariable Long postId, @AuthenticationPrincipal PrincipalDetails principal) {
        User user = principal.getUser();
        postLikeService.unlike(postId, user.getId());
    }

    // My Page ------------------------------------------------------------------------
    // 내가 쓴 게시물 리스트
    @GetMapping("/user/{userId}/posts")
    public List<ResponseDTO.PostResponse> myPosts(@AuthenticationPrincipal PrincipalDetails principal) {
        return postService.findAllByUserId(principal.getUser().getId());
    }

    // 내가 추천한 게시물 리스트
    @GetMapping("/user/{userId}/posts/like")
    public List<ResponseDTO.PostResponse> myLikePosts(@AuthenticationPrincipal PrincipalDetails principal) {
        return postService.findAllByLikePosts(principal.getUser().getId());
    }
}
