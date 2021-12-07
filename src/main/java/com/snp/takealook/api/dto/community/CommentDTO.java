package com.snp.takealook.api.dto.community;

import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommentDTO {

    @Getter
    public static class Create {
        private Long postId;
        private Long writerId;
        private String content;

        @Builder
        public Create(Long postId, Long writerId, String content) {
            this.postId = postId;
            this.writerId = writerId;
            this.content = content;
        }

        public Comment toEntity(Post post, User writer) {
            return Comment.builder()
                    .post(post)
                    .writer(writer)
                    .content(content)
                    .build();
        }
    }

    @Getter
    public static class Get {
        private Post post;
        private User writer;
        private String content;
        private LocalDateTime modifiedAt;

        public Get(Comment entity) {
            this.post = entity.getPost();
            this.writer = entity.getWriter();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class Update {
        private String content;
    }
}
