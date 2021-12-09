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
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Selection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private Boolean dflag;

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
        this.dflag = false;
    }

    public Selection updateCatLocationList(List<CatLocation> catLocationList) {
        this.catLocationList = catLocationList;

        return this;
    }

}
