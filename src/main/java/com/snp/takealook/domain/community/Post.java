package com.snp.takealook.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Board board;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;

    @NotNull
    private String title;

    @Lob @NotNull
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<PostLike> postLikeList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<PostImage> postImageList;

    @Builder
    public Post(Board board, User writer, String title, String content) {
        this.board = board;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public Post update(String title) {
        this.title = title;

        return this;
    }

}
