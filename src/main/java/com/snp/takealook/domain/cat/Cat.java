package com.snp.takealook.domain.cat;

import com.snp.takealook.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cat {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn
    private User user;

//    private Group group

    @NonNull
    private String name;

    @NonNull
    private byte neutered;

    @NonNull
    private byte status;

    @NonNull
    @Column(name="created_at")
    @CreatedDate
    private LocalDate createdAt;

    @NonNull
    @Column(name="modifiend_at")
    @LastModifiedDate
    private LocalDate modifiedAt;

    @NonNull
    @Column(name="d_flag")
    private byte dFlag;
  
}
