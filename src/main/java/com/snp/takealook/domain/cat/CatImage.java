package com.snp.takealook.domain.cat;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
public class CatImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private Cat cat;

    @NonNull
    private byte[] image;
}
