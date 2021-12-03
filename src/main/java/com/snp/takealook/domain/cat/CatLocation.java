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
public class CatLocation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Builder
    public CatLocation(Cat cat, Double latitude, Double longitude) {
        this.cat = cat;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
