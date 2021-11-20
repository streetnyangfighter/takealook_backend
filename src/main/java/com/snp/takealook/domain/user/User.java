package com.snp.takealook.domain.user;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatImage;
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

    @NotNull
    private String loginPw;

    @NotNull
    private String nickname;

    @NotNull
    private String phone;

    @NotNull
    private byte[] image;

    @NotNull
    private byte type;

    @NotNull
    private boolean dFlag;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cat> catList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notificationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CatImage> catImageList;
}
