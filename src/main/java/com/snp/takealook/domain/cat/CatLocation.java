package com.snp.takealook.domain.cat;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class CatLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    @CreationTimestamp
    private LocalDateTime created_at;
}
