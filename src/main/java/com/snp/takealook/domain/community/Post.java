package com.snp.takealook.domain.community;

import com.snp.takealook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
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

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikeList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImageList;

}
