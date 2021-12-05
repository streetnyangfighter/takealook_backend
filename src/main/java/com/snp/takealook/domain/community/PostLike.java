package com.snp.takealook.domain.community;

import com.snp.takealook.domain.BaseTimeEntity;
import com.snp.takealook.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Post post;

    @NotNull
    @ManyToOne
    private User user;

    @Builder
    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }

}
