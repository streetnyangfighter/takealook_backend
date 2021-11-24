package com.snp.takealook.domain.community;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Post post;

    @NotNull
    @Lob
    private byte[] image;

    @NotNull
    private String fileName;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

}
