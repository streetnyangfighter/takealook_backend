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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String loginId;

    @NotNull
    private String nickname;

    private String phone;

    @NotNull
    @Lob
    private String image;

    @NotNull
    private String loginType;

    @NotNull
    private Boolean dFlag;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
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
    public User(String loginId, String nickname, String phone, String image, String loginType, Boolean dFlag, Role role) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.phone = phone;
        this.image = image;
        this.loginType = loginType;
        this.dFlag = false;
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

    public User delete(Boolean dFlag) {
        this.dFlag = dFlag;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public User updateLocations(List<UserLocation> userLocationList) {
        this.userLocationList = userLocationList;

        return this;
    }

}
