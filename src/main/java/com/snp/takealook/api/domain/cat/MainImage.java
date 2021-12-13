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
public class MainImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
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
    public MainImage(Cat cat, String originFileName, String fileName, String contentType, Long fileSize, String filePath) {
        this.cat = cat;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.filePath = filePath;
    }
}
