package com.snp.takealook.domain.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn
    private User user;

    @NonNull
    private String message;

    @NonNull
    private byte type;

    @NonNull
    private LocalDate time;

    @NonNull
    private byte checked;
}
