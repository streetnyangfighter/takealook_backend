package com.snp.takealook.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.domain.BaseTimeEntity;
import com.snp.takealook.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Post post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @NotNull
    @Lob
    private String content;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
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
