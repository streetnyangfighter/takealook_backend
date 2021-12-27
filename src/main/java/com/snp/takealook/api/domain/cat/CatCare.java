package com.snp.takealook.api.domain.cat;

import com.snp.takealook.api.domain.BaseTimeEntity;
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
    private Selection selection;

    @NotNull
    private Byte type;
    // 0: 밥, 1: 간식, 2: 약, 3: 병원, 4: 기타

    @NotNull
    private String message;

    @Builder
    public CatCare(Selection selection, Byte type, String message) {
        this.selection = selection;
        this.type = type;
        this.message = message;
    }

    public CatCare update(Byte type, String message) {
        this.type = type;
        this.message = message;

        return this;
    }
}
