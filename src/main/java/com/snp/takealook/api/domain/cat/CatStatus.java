package com.snp.takealook.api.domain.cat;

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
public class CatStatus extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private Byte status;
    // 0: 건강함, 1: 치료 필요, 2: 입양, 3: 사망

    @Builder
    public CatStatus(Cat cat, Byte status) {
        this.cat = cat;
        this.status = status;
    }
}
