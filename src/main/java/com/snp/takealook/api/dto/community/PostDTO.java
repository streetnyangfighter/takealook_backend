package com.snp.takealook.api.dto.community;

import com.snp.takealook.api.domain.community.Board;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class PostDTO {

    @Getter
    public static class Create {
        private Long boardId;
        private Long writerId;
        private String title;
        private String content;
        private String imgUrl;

        @Builder
        public Create(Long boardId, Long writerId, String title, String content, String imgUrl) {
            this.boardId = boardId;
            this.writerId = writerId;
            this.title = title;
            this.content = content;
            this.imgUrl = imgUrl;
        }

        public Post toEntity(Board board, User writer, String imgUrl) {
            return Post.builder()
                    .board(board)
                    .writer(writer)
                    .title(title)
                    .content(content)
                    .imgUrl(imgUrl)
                    .build();
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
