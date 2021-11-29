package com.snp.takealook.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CatGroup matchedGroup;

    private byte status;
    // 0: 거절, 1: 수락, 2: 수락 이전(보류)

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "matchedGroup", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatGroup> groupList;

    @Builder
    public CatGroup() {
    }

}
