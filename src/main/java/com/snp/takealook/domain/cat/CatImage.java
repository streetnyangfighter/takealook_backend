package com.snp.takealook.domain.cat;

import com.snp.takealook.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @ManyToOne
    private User user;

    @NonNull
    private byte[] image;

    @NonNull
    private String fileName;

    @NonNull
    private LocalDateTime createdAt;
}
