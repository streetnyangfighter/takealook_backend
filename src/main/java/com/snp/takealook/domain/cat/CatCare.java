package com.snp.takealook.domain.cat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class CatCare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private Cat cat;

    @NonNull
    private LocalDateTime datetime;

    @NonNull
    private byte type;

    @NonNull
    private String message;

    @NonNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NonNull
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
