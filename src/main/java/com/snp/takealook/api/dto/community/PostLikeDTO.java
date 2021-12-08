package com.snp.takealook.api.dto.community;

import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.community.PostLike;
import com.snp.takealook.api.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class PostLikeDTO {

    @Getter
    public static class Like {
        private Long postId;
        private Long userId;

        @Builder
        public Like(Long postId, Long userId) {
            this.postId = postId;
            this.userId = userId;
        }

        public PostLike toEntity(Post post, User user) {
            return PostLike.builder()
                    .post(post)
                    .user(user)
                    .build();
        }
    }
}
