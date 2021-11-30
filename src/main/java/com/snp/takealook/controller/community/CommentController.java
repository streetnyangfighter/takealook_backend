package com.snp.takealook.controller.community;

import com.snp.takealook.service.community.CommentService;
import com.snp.takealook.service.community.PostService;
import com.snp.takealook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    // 댓글 작성

    // 게시글 댓글 리스트 조회

    // 댓글 수정

    // 댓글 삭제
}
