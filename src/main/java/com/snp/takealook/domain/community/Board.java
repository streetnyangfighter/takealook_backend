package com.snp.takealook.domain.community;

import lombok.*;

import javax.persistence.*;


@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
}
