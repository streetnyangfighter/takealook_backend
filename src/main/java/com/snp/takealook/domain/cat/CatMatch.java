package com.snp.takealook.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CatMatch {

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
    private byte status;
    // 0: 거절, 1: 수락, 2: 수락 이전(보류)

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Builder
    public CatMatch(Cat proposer, Cat accepter) {
        this.proposer = proposer;
        this.accepter = accepter;
        this.status = 2;
    }

    public CatMatch accept() {
        this.status = 1;

        return this;
    }

    public CatMatch reject() {
        this.status = 0;

        return this;
    }

}
