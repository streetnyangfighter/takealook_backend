package com.snp.takealook.domain.user;

import com.snp.takealook.domain.cat.Cat;

import com.snp.takealook.domain.community.Comment;
import com.snp.takealook.domain.community.CommentLike;
import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.community.PostLike;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String loginId;

//    @NotNull
//    private String loginPw;

    @NotNull
    private String nickname;

//    @NotNull
//    private String phone;

    @NotNull
    private String image;

//    @NotNull
//    private byte type;

    @NotNull
    private boolean dFlag;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

//    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

//    @NotNull
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cat> catList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Post> postList;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    private List<Comment> commentListList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikeListList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLike> postLikeListList;

    @Builder
    public User(String loginId, String nickname, String image, boolean dFlag, Role role) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.image = image;
        this.dFlag = true;
        this.role = role;
    }

    public User update(String nickname, String image) {
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
