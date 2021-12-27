package com.snp.takealook.api.domain.user;

import com.snp.takealook.api.domain.BaseTimeEntity;
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

    @NotNull
    private Boolean checked;

    @NotNull
    private Long linkedId;

    @Builder
    public Notification(User user, String message, Byte type, Long linkedId) {
        this.user = user;
        this.message = message;
        this.type = type;
        this.checked = false;
        this.linkedId = linkedId;
    }

    public Notification check() {
        this.checked = true;

        return this;
    }
}