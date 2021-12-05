package com.snp.takealook.dto.community;

import com.snp.takealook.domain.community.Board;
import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class PostDTO {

    @Getter
    public static class Create {
        private Long boardId;
        private Long writerId;
        private String title;
        private String content;

        @Builder
        public Create(Long boardId, Long writerId, String title, String content) {
            this.boardId = boardId;
            this.writerId = writerId;
            this.title = title;
            this.content = content;
        }

        public Post toEntity(Board board, User writer) {
            return Post.builder()
                    .board(board)
                    .writer(writer)
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    @Getter
    public static class Get {
        private Board board;
        private User writer;
        private String title;
        private String content;
        private LocalDateTime modifiedAt;
        // 댓글, 추천

        public Get(Post entity) {
            this.board = entity.getBoard();
            this.writer = entity.getWriter();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class Update {
        private String title;
        private String content;

        public Update(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

}
