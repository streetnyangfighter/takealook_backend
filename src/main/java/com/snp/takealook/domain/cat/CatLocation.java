package com.snp.takealook.domain.cat;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class CatLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private Cat cat;

    @NonNull
    private double latitude;

    @NonNull
    private double longitude;

    @NonNull
    private LocalDateTime created_at;
}
