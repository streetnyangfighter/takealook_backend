package com.snp.takealook.domain.user;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    private Byte type;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

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