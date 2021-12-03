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
public class CatImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private String originFileName;

    @NotNull
    private String fileName;

    @NotNull
    private String contentType;

    @NotNull
    private Long fileSize;

    @NotNull
    private String filePath;

    @Builder
    public CatImage(Cat cat, String originFileName, String fileName, String contentType, String filePath, Long fileSize) {
        this.cat = cat;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.contentType = contentType;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
