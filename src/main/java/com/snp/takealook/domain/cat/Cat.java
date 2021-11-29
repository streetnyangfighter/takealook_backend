package com.snp.takealook.domain.cat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.snp.takealook.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cat {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @OneToOne
    private CatGroup catGroup;

    @NotNull
    private String name;

    @NotNull
    private byte neutered;

    @NotNull
    private byte status;

    @NotNull
    private boolean dFlag;

    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatCare> catCareList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatLocation> catLocationList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CatImage> catImageList;

    @Builder
    public Cat(User user, CatGroup catGroup, String name, byte neutered, byte status, boolean dFlag) {
        this.user = user;
        this.catGroup = catGroup;
        this.name = name;
        this.neutered = neutered;
        this.status = status;
        this.dFlag = false;
    }
  
}
