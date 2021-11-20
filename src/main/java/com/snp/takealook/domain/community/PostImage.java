package com.snp.takealook.domain.community;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Post post;

    @NotNull
    private String origName;

    @NotNull
    private String name;

    @NotNull
    private String path;

    @NotNull
    private Integer size;

    @NotNull
    private byte[] image;

}
