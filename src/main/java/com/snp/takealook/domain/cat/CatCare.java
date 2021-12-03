package com.snp.takealook.domain.cat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CatCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private Byte type;
    // 0: 밥, 1: 간식, 2: 약, 3: 병원

    @NotNull
    private String message;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Builder
    public CatCare(Cat cat, Byte type, String message) {
        this.cat = cat;
        this.type = type;
        this.message = message;
    }

    public CatCare update(Byte type, String message) {
        this.type = type;
        this.message = message;

        return this;
    }
}
