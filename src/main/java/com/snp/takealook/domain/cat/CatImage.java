package com.snp.takealook.domain.cat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CatImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Cat cat;

    @NotNull
    private String originFileName;

    @NotNull
    private String contentType;

    @NotNull
    private Long fileSize;

    @NotNull
    private String filePath;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public CatImage(Cat cat, String originFileName, String contentType, String filePath, Long fileSize) {
        this.cat = cat;
        this.originFileName = originFileName;
        this.contentType = contentType;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
