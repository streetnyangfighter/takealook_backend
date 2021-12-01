package com.snp.takealook.service.community;

import com.snp.takealook.domain.community.Comment;
import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.community.CommentDTO;
import com.snp.takealook.repository.community.CommentRepository;
import com.snp.takealook.repository.community.PostRepository;
import com.snp.takealook.repository.user.UserRepository;
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
    @Transactional
    public Long save(Long postId, CommentDTO.Create dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + " is not valid"));

        User writer = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + dto.getWriterId() + " is not valid"));

        return commentRepository.save(dto.toEntity(post, writer)).getId();
    }

    @Transactional(readOnly = true)
    public List<CommentDTO.Get> findAllPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + " is not valid"));

        return commentRepository.findAllByPost(post).stream().map(CommentDTO.Get::new).collect(Collectors.toList());
    }

    // 댓글 수정
    @Transactional
    public Long update(Long commentId, CommentDTO.Update dto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id: " + commentId + " is not valid"));

        comment.update(dto.getContent());

        return commentId;
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id: " + id + " is not valid"));

        commentRepository.delete(comment);
    }
}
