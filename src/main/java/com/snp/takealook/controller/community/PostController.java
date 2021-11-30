package com.snp.takealook.controller.community;

import com.snp.takealook.domain.community.Board;
import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.community.PostDTO;
import com.snp.takealook.service.community.PostService;
import com.snp.takealook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

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
}
