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
public class CatLocation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Selection selection;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @Builder
    public CatLocation(Selection selection, Double latitude, Double longitude) {
        this.selection = selection;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
