package com.snp.takealook.api.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @NotNull
    private String title;

    @Lob @NotNull
    private String content;

    @NotNull
    private String imgUrl;

    @NotNull
    private Boolean checkLike;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<PostLike> postLikeList;

    @Builder
    public Post(Board board, User writer, String title, String content, String imgUrl) {
        this.board = board;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.checkLike = false;
    }

    public Post update(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }

    public Post updateThumbnail(String imgUrl) {
        this.imgUrl = imgUrl;

        return this;
    }

    public Post checkLike(Boolean checkLike) {
        this.checkLike = checkLike;

        return this;
    }

}
