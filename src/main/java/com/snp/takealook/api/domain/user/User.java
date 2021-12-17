package com.snp.takealook.api.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Selection;
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
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @Lob
    private String image;

    @NotNull
    private ProviderType providerType;

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

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Post> postList;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> commentListList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<CommentLike> commentLikeList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<PostLike> postLikeList;

    @Builder
    public User(String username, String password, String email, String nickname, String image, ProviderType providerType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.image = image;
        this.providerType = providerType;
        this.dflag = false;
        this.role = Role.USER;
    }

    public User update(String nickname, String image) {
        this.nickname = nickname;
        this.image = image;

        return this;
    }

    public User updateDetail(String nickname, String image) {
        this.nickname = nickname;
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
  
}
