package com.snp.takealook.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.domain.cat.Cat;

import com.snp.takealook.domain.community.Comment;
import com.snp.takealook.domain.community.CommentLike;
import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.community.PostLike;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Lob
    private String image;

//    @NotNull
//    private byte type;

    @NotNull
    private boolean dFlag;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

//    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

//    @NotNull
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Cat> catList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<UserLocation> userLocationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Post> postList;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Comment> commentListList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CommentLike> commentLikeListList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<PostLike> postLikeListList;

    @Builder
    public User(String loginId, String nickname, String image, boolean dFlag, Role role) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.image = image;
        this.dFlag = false;
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
