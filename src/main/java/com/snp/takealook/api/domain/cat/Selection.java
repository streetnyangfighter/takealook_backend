package com.snp.takealook.api.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.api.domain.BaseTimeEntity;
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
public class Selection extends BaseTimeEntity {

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

    public Selection update(Cat cat) {
        this.cat = cat;

        return this;
    }

    public Selection delete() {
        if (this.dflag) {
            throw new IllegalStateException("이미 도감에서 삭제 되었습니다.");
        }

        this.dflag = true;
        return this;
    }

    public Selection updateCatLocationList(List<CatLocation> catLocationList) {
        this.catLocationList = catLocationList;

        return this;
    }

}
