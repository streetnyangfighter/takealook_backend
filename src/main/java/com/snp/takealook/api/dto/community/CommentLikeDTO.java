package com.snp.takealook.api.dto.community;

import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.CommentLike;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class CommentLikeDTO {

    @Getter
    public static class Like {
        private Long commentId;
        private Long userId;

        @Builder
        public Like(Long commentId, Long userId) {
            this.commentId = commentId;
            this.userId = userId;
        }

        public CommentLike toEntity(Comment comment, User user) {
            return CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
        }
    }
}
