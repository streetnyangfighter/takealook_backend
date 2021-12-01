package com.snp.takealook.service.community;

import com.snp.takealook.domain.community.Board;
import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.dto.community.PostDTO;
import com.snp.takealook.repository.community.BoardRepository;
import com.snp.takealook.repository.community.PostRepository;
import com.snp.takealook.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 게시글 등록
    @Transactional
    public Long save(PostDTO.Create dto) {

        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board with id: " + dto.getBoardId() + " is not valid"));

        User user = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + dto.getWriterId() + " is not valid"));

        return postRepository.save(dto.toEntity(board, user)).getId();

    }

    // 게시글 리스트 조회
    @Transactional(readOnly = true)
    public List<PostDTO.Get> findAllByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board with id: " + boardId + " is not valid"));

        return postRepository.findAllByBoard(board).stream().map(PostDTO.Get::new).collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " is not valid"));

        return post;
    }

    // 게시글 수정
    @Transactional
    public Long update(Long id, PostDTO.Update dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " is not valid"));

        post.update(dto.getTitle());

        return id;
    }

    // 게시글 삭제
    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " is not valid"));

        postRepository.delete(post);
    }

}
