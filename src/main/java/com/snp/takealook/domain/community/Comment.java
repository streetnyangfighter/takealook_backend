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
public class Comment {

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

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeList;

}
