package com.snp.takealook.api.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.cat.Cat;

import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.CommentLike;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.community.PostLike;

import com.snp.takealook.oauth.entity.ProviderType;
import com.snp.takealook.oauth.entity.RoleType;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @NotNull
    private Boolean dFlag;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;

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
    public User(String loginId, String nickname, String phone, String image, ProviderType providerType, Boolean dFlag, RoleType roleType) {
        this.loginId = loginId;
        this.password = "NO_PASS";
        this.nickname = nickname;
        this.phone = phone;
        this.image = image;
        this.providerType = providerType;
        this.dFlag = false;
        this.roleType = roleType;
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

    public User delete(Boolean dFlag) {
        this.dFlag = dFlag;

        return this;
    }

    public String getRoleKey() {
        return this.roleType.getDisplayName();
    }

    public User updateLocations(List<UserLocation> userLocationList) {
        this.userLocationList = userLocationList;

        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
