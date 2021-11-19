package com.snp.takealook.domain.cat;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class CatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private CatGroup matchedGroup;

    @NonNull
    private byte status;

    @NonNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NonNull
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @NonNull
    @OneToMany(mappedBy = "matchedGroup", cascade = CascadeType.ALL)
    private List<CatGroup> groupList;

}
