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
public class CatImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Selection selection;

    @NotNull
    private String path;

    @Builder
    public CatImage(Selection selection, String path) {
        this.selection = selection;
        this.path = path;
    }
}
