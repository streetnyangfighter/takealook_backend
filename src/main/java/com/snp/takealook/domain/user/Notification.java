package com.snp.takealook.domain.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private String message;

    @NotNull
    private byte type;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    private byte checked;

    @Builder
    public Notification(User user, String message, byte type, byte checked) {
        this.user = user;
        this.message = message;
        this.type = type;
        this.checked = checked;
    }
}