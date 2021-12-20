package com.snp.takealook.api.service.community;

import com.snp.takealook.api.domain.community.Board;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.community.PostLike;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.community.PostDTO;
import com.snp.takealook.api.repository.community.BoardRepository;
import com.snp.takealook.api.repository.community.PostLikeRepository;
import com.snp.takealook.api.repository.community.PostRepository;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.api.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;
    private final PostLikeRepository postLikeRepository;

    // 게시글 등록
    @Transactional(rollbackFor = Exception.class)
    public Long save(PostDTO.Create dto, String imgUrl) {

        Board board = boardRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Board with id: " + dto.getBoardId() + " is not valid"));

        User user = userRepository.findById(dto.getWriterId())
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + dto.getWriterId() + " is not valid"));

        return postRepository.save(dto.toEntity(board, user, imgUrl)).getId();

    }

    // 게시글 리스트 조회 (게시판 구분 x)
    @Transactional(readOnly = true)
    public List<ResponseDTO.PostResponse> findAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream().map(ResponseDTO.PostResponse::new).collect(Collectors.toList());
    }

    // 게시글 리스트 조회 (게시판별)
    @Transactional(readOnly = true)
    public List<ResponseDTO.PostResponse> findAllByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board with id: " + boardId + " is not valid"));

        return postRepository.findAllByBoardOrderByCreatedAtDesc(board).stream().map(ResponseDTO.PostResponse::new).collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public ResponseDTO.PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + id + " is not valid"));

        return new ResponseDTO.PostResponse(post);
    }

    // 게시글 수정
    @Transactional(rollbackFor = Exception.class)
    public Long update(Long postId, Long userId, PostDTO.Update dto, String imgUrl) {
        Post post = postRepository.findPostByIdAndUser_Id(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + "and User with id: " + userId + " is not valid"));

        post.update(dto.getTitle(), dto.getContent());

        if(imgUrl != null) {
            s3Uploader.fileDelete(post.getImgUrl());
            post.updateThumbnail(imgUrl);
        }

        return postId;
    }

    // 게시글 삭제
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long postId, Long userId) {
        Post post = postRepository.findPostByIdAndUser_Id(postId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + "and User with id: " + userId + " is not valid"));

        s3Uploader.fileDelete(post.getImgUrl());

        postRepository.delete(post);
    }

    // 내가 쓴 게시물 리스트
    @Transactional(readOnly = true)
    public List<ResponseDTO.PostResponse> findAllByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        return postRepository.findAllByWriter(user).stream().map(ResponseDTO.PostResponse::new).collect(Collectors.toList());
    }

    // 내가 추천한 게시물 리스트
    @Transactional(readOnly = true)
    public List<ResponseDTO.PostResponse> findAllByLikePosts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " is not valid"));

        List<ResponseDTO.PostResponse> result = new ArrayList<>();
        List<PostLike> likePosts = postLikeRepository.findAllByUser(user);

        for(PostLike likePost : likePosts) {
            Post post = postRepository.findById(likePost.getPost().getId()).orElseThrow(() -> new IllegalArgumentException("User with id: " + likePost.getPost().getId() + " is not valid"));
            result.add(new ResponseDTO.PostResponse(post));
        }

        return result;
    }
}
