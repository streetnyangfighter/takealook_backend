package com.snp.takealook.domain.community;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.domain.user.User;
import lombok.AccessLevel;
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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CommentLike> commentLikeList;

}
