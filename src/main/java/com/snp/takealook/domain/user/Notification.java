package com.snp.takealook.domain.user;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    private String message;

    @NotNull
    private byte type;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    private byte checked;
}
