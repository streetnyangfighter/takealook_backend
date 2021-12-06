package com.snp.takealook.domain.cat;

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
public class CatMatch extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat proposer;

    @NotNull
    @ManyToOne
    private Cat accepter;

    @NotNull
    private Byte status;
    // 0: 거절, 1: 수락, 2: 수락 이전(보류)

    @Builder
    public CatMatch(Cat proposer, Cat accepter) {
        this.proposer = proposer;
        this.accepter = accepter;
        this.status = 2;
    }

    public CatMatch accept() {
        if (this.status == 2) {
            this.status = 1;
        } else {
            throw new IllegalStateException("already accepted/rejected");
        }

        return this;
    }

    public CatMatch reject() {
        if (this.status == 2) {
            this.status = 0;
        } else {
            throw new IllegalStateException("already accepted/rejected");
        }

        return this;
    }

}
