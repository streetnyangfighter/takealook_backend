package com.snp.takealook.api.domain.community;

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
public class PostImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Post post;

    @NotNull
    private String orignFileName; // 원본명

    @NotNull
    private String contentType;

    @NotNull
    private String filePath; // 파일 저장 경로

    @NotNull
    private Long fileSize;

    @Builder
    public PostImage(Post post, String orignFileName, String contentType, String filePath, Long fileSize) {
        this.post = post;
        this.orignFileName = orignFileName;
        this.contentType = contentType;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

}
