package com.snp.takealook.api.service.community;

import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.community.CommentDTO;
import com.snp.takealook.api.repository.community.CommentRepository;
import com.snp.takealook.api.repository.community.PostRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 작성
    @Transactional(rollbackFor = Exception.class)
    public Long save(Long postId, CommentDTO.Create dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + " is not valid"));

        User writer = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + dto.getWriterId() + " is not valid"));

        return commentRepository.save(dto.toEntity(post, writer)).getId();
    }

    // 댓글 리스트 조회
    @Transactional(readOnly = true)
    public List<ResponseDTO.CommentResponse> findAllPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + " is not valid"));

        return commentRepository.findAllByPostOrderByCreatedAtAsc(post).stream().map(ResponseDTO.CommentResponse::new).collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional(rollbackFor = Exception.class)
    public Long update(Long commentId, Long userId, CommentDTO.Update dto) {
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        Comment comment = commentRepository.findByIdAndWriter(commentId, writer)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id: " + commentId + "and User with id: " + userId + " is not valid"));

        comment.update(dto.getContent());

        return commentId;
    }

    // 댓글 삭제
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long commentId, Long userId) {
        User writer = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        Comment comment = commentRepository.findByIdAndWriter(commentId, writer)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id: " + commentId + "and User with id: " + userId + " is not valid"));

        commentRepository.delete(comment);
    }
}
