package com.snp.takealook.domain.cat;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class CatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private CatGroup matchedGroup;

    @NotNull
    private byte status;

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @NotNull
    @OneToMany(mappedBy = "matchedGroup", cascade = CascadeType.ALL)
    private List<CatGroup> groupList;

}
