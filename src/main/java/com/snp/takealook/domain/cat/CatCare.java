package com.snp.takealook.domain.cat;

import com.snp.takealook.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CatCare extends BaseTimeEntity {

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
