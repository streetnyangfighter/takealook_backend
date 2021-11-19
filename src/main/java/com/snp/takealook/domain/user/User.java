package com.snp.takealook.domain.user;

import com.snp.takealook.domain.cat.Cat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name="login_id")
    private String loginId;

    @NonNull
    @Column(name="login_pw")
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
    @Column(name="d_flag")
    private byte dFlag;

    @NonNull
    @Column(name="created_at")
    @CreatedDate
    private LocalDate createdAt;

    @NonNull
    @Column(name="modified_at")
    @LastModifiedDate
    private LocalDate modifiedAt;

    @OneToMany(mappedBy = "user")
    private List<Cat> catList;
}
