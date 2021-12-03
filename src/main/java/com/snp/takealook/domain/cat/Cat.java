package com.snp.takealook.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cat {
  
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
    private Boolean dFlag;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

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
    public Cat(User user, String name, Byte gender, Byte neutered, Byte status) {
        this.user = user;
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;
        this.dFlag = false;
    }

    public Cat updateInfo(String name, Byte gender, Byte neutered, Byte status) {
        this.name = name;
        this.gender = gender;
        this.neutered = neutered;
        this.status = status;

        return this;
    }

    public Cat updateStatus(Byte status) {
        this.status = status;

        return this;
    }

    public Cat delete() {
        this.dFlag = true;

        return this;
    }

    public Cat restore() {
        this.dFlag = false;

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
