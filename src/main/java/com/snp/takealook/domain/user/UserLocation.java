package com.snp.takealook.domain.user;

import com.snp.takealook.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLocation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private String sido;

    @NotNull
    String gugun;

    @NotNull
    String dong;

    @Builder
    public UserLocation(User user, String sido, String gugun, String dong) {
        this.user = user;
        this.sido = sido;
        this.gugun = gugun;
        this.dong = dong;
    }

}
