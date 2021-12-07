package com.snp.takealook.api.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
import com.snp.takealook.api.domain.user.User;
import lombok.*;

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
    @ManyToOne
    private User user;

    @ManyToOne
    private CatGroup catGroup;

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
    private String info;

    @NotNull
    private Boolean dFlag;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatCare> catCareList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatLocation> catLocationList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatImage> catImageList;

    @OneToMany(mappedBy = "proposer", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatMatch> catProposerList;

    @OneToMany(mappedBy = "accepter", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatMatch> catAccepterList;

    @Builder
    public Cat(User user, String name, Byte gender, Byte neutered, Byte status, String info) {
        this.user = user;
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;
        this.info = info;
        this.dFlag = false;
    }

    public Cat updateInfo(String name, Byte gender, Byte neutered, Byte status, String info) {
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;
        this.info = info;

        return this;
    }

    public Cat updateStatus(Byte status) {
        this.status = status;

        return this;
    }

    public Cat delete() {
        if (!this.dFlag) {
            this.dFlag = true;
        } else {
            throw new IllegalStateException("already deleted");
        }

        return this;
    }

    public Cat restore() {
        if (this.dFlag) {
            this.dFlag = false;
        } else {
            throw new IllegalStateException("delete first");
        }

        return this;
    }

    public Cat updateLocations(List<CatLocation> catLocationList) {
        this.catLocationList = catLocationList;

        return this;
    }

    public Cat updateCatGroup(CatGroup matchedGroup) {
        this.catGroup = matchedGroup;

        return this;
    }
  
}
