package com.snp.takealook.api.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.Selection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Byte gender;
    // 0: 남, 1: 여, 2: 자웅동체 혹은 확인 불가

    @NotNull
    private Byte neutered;
    // 0: 중성화 X, 1: 중성화 O, 2: 확인 불가

    @NotNull
    private Byte status;
    // 0: 건강함, 1: 치료 필요, 2: 입양, 3: 사망

    @NotNull
    private Byte pattern;
    // 0: 치즈 태비, 1: 고등어 태비, 2: 젖소, ...

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Selection> selectionList;

    @Builder
    public Cat(String name, Byte gender, Byte neutered, Byte status, Byte pattern) {
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;
        this.pattern = pattern;
    }

    public Cat updateInfo(String name, Byte gender, Byte neutered, Byte status, Byte pattern) {
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;
        this.pattern = pattern;

        return this;
    }

    public Cat changeStatus(Byte status) {
        this.status = status;

        return this;
    }

}
