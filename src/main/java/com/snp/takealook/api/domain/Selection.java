package com.snp.takealook.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.domain.cat.CatImage;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Selection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Cat cat;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "selection", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatCare> catCareList;

    @OneToMany(mappedBy = "selection", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatImage> catImageList;

    @OneToMany(mappedBy = "selection", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatLocation> catLocationList;

    @Builder
    public Selection(User user, Cat cat) {
        this.user = user;
        this.cat = cat;
    }

    public Selection update(Cat cat) {
        this.cat = cat;

        return this;
    }

    public Selection softDelete() {
        if (Objects.equals(this.getUser(), null)) {
            throw new IllegalStateException("이미 삭제된 간택 내역입니다.");
        }

        this.user = null;

        return this;
    }

    public Selection updateCatLocationList(List<CatLocation> catLocationList) {
        this.catLocationList = catLocationList;

        return this;
    }

}
