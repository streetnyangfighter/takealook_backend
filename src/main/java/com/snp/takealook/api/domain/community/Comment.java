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
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @NotNull
    @Lob
    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<CommentLike> commentLikeList;

    @Builder
    public Comment(Post post, User writer, String content) {
        this.post = post;
        this.writer = writer;
        this.content = content;
    }

    public Comment update(String content) {
        this.content = content;

        return this;
    }
}
