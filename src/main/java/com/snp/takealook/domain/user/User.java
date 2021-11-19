package com.snp.takealook.domain.user;

import com.snp.takealook.domain.cat.Cat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String loginId;

    @NonNull
    private String loginPw;

    @NonNull
    private String nickname;

    @NonNull
    private String phone;

    @NonNull
    private byte[] image;

    @NonNull
    private byte type;

    @NonNull
    private byte dFlag;

    @NonNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NonNull
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @NonNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cat> catList;

    @NonNull
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notificationList;

}
