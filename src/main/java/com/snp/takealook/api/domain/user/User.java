package com.snp.takealook.api.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.Selection;
import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.CommentLike;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.community.PostLike;
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
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String nickname;

    private String phone;

    @NotNull
    @Lob
    private String image;

    @NotNull
    private String loginType;

    @NotNull
    private Boolean dflag;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Selection> selectionList;

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
    public User(String loginId, String nickname, String phone, String image, String loginType, Role role) {
        this.loginId = loginId;
        this.password = "NO_PASS";
        this.nickname = nickname;
        this.phone = phone;
        this.image = image;
        this.loginType = loginType;
        this.dflag = false;
        this.role = role;
    }

    public User update(String nickname, String image) {
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public User updateDetail(String nickname, String phone, String image) {
        this.nickname = nickname;
        this.phone = phone;
        this.image = image;

        return this;
    }

    public User delete() {
        if (this.dflag) {
            throw new IllegalStateException("이미 탈퇴한 회원입니다.");
        }
        this.dflag = true;

        return this;
    }

    public User restore() {
        if (!this.dflag) {
            throw new IllegalStateException("탈퇴하지 않은 회원입니다");
        }
        this.dflag = false;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
  
}
