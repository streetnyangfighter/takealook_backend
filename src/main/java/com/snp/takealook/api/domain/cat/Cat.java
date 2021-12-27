package com.snp.takealook.api.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.dto.cat.CatDTO;
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
    // 0: 건강함, 1: 치료 필요

    @NotNull
    private Byte pattern;
    // 0: 치즈 태비, 1: 고등어 태비, 2: 젖소, ...

    @NotNull
    private Boolean dflag;
    // 사망 여부

    @Column(name = "dmsg")
    private String dMsg;

    @NotNull
    private Boolean aflag;
    // 입양 여부

    @Column(name = "a_msg")
    private String aMsg;

    private String image;

    @NotNull
    private Double leftEarX;

    @NotNull
    private Double leftEarY;

    @NotNull
    private Double rightEarX;

    @NotNull
    private Double rightEarY;

    @NotNull
    private Double leftEyeX;

    @NotNull
    private Double leftEyeY;

    @NotNull
    private Double rightEyeX;

    @NotNull
    private Double rightEyeY;

    // 고양이를 삭제하면 간택 내역 - 연결된 정보 일괄 삭제 되도록 처리
    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Selection> selectionList;

    @Builder
    public Cat(String name, Byte gender, Byte neutered, Byte status, Byte pattern, String image,
               Double leftEarX, Double leftEarY,
               Double rightEarX, Double rightEarY,
               Double leftEyeX, Double leftEyeY,
               Double rightEyeX, Double rightEyeY) {
        if (status != (byte) 0 && status != (byte) 1) {
            throw new IllegalStateException("존재하지 않는 상태입니다.");
        }
        if (name.equals("")) {
            throw new IllegalStateException("이름은 공백일 수 없습니다.");
        }
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;
        this.pattern = pattern;
        this.dflag = false;
        this.aflag = false;
        this.image = image;
        this.leftEarX = leftEarX;
        this.leftEarY = leftEarY;
        this.rightEarX = rightEarX;
        this.rightEarY = rightEarY;
        this.leftEyeX = leftEyeX;
        this.leftEyeY = leftEyeY;
        this.rightEyeX = rightEyeX;
        this.rightEyeY = rightEyeY;
    }

    public Cat updateInfo(CatDTO.Update catInfo) {
        if (name.equals("")) {
            throw new IllegalStateException("이름은 공백일 수 없습니다.");
        }
        this.name = catInfo.getName();
        this.gender = catInfo.getGender();
        this.neutered = catInfo.getNeutered();
        this.pattern = catInfo.getPattern();

        return this;
    }

    public Cat updateCatPoints(CatDTO.CatPoint catPoints) {
        this.leftEarX = catPoints.getLeftEarX();
        this.leftEarY = catPoints.getLeftEarY();
        this.rightEarX = catPoints.getRightEarX();
        this.rightEarY = catPoints.getRightEarY();
        this.leftEyeX = catPoints.getLeftEyeX();
        this.leftEyeY = catPoints.getLeftEyeY();
        this.rightEyeX = catPoints.getRightEyeX();
        this.rightEyeY = catPoints.getRightEyeY();

        return this;
    }

    public Cat updateImage(String image) {
        this.image = image;

        return this;
    }

    public Cat changeStatus(Byte status) {
        if (status != (byte) 0 && status != (byte) 1) {
            throw new IllegalStateException("존재하지 않는 상태입니다.");
        }
        this.status = status;

        return this;
    }

    public Cat sendCatStar(String msg) {
        if (this.dflag) {
            throw new IllegalStateException("이미 고양이 별에 간 고양이입니다.");
        }

        this.dflag = true;
        this.dMsg = msg;

        return this;
    }

    public Cat adopt(String msg) {
        if (this.aflag) {
            throw new IllegalStateException("이미 입양된 고양이입니다.");
        }

        this.aflag = true;
        this.aMsg = msg;

        return this;
    }

}
