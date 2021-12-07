package com.snp.takealook.domain.user;

import com.snp.takealook.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private String message;

    @NotNull
    private Byte type;
    // 1: 돌봄내역 추가 알림
        // 00 님이 00(이)의 돌봄이력을 추가했습니다.
    // 2: 고양이 상태 변경 알림
        // 00 님이 00(이)의 상태를 변경했습니다.
    // 3: 매칭 신청 알림
        // 00(이)에게 매칭 신청이 도착했습니다.
    // 4: 매칭 신청 수락/거절 알림
        // 00(이)가 00(이)에게 보냈던 매칭이 수락/거절 되었습니다.
    // 5: 그룹 변경 알림 - 합쳐졌을 때
        // 00(이)와 같은 고양이가 발견되어 매칭되었습니다.
    // 6: 그룹 변경 알림 - 누군가 그룹에서 나갔을 때
        // 00 님이 00(이) 그룹에서 나갔습니다.

    @NotNull
    private Boolean checked;

    @Builder
    public Notification(User user, String message, Byte type) {
        this.user = user;
        this.message = message;
        this.type = type;
        this.checked = false;
    }

    public Notification check() {
        this.checked = true;

        return this;
    }
}