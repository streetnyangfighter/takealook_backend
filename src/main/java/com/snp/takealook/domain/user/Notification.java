package com.snp.takealook.domain.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    private String message;

    @NonNull
    private byte type;

    @NonNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NonNull
    private byte checked;
}
