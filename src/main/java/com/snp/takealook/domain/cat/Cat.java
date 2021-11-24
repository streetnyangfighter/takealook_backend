package com.snp.takealook.domain.cat;

import com.snp.takealook.domain.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
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
    private List<CatCare> catCareList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    private List<CatLocation> catLocationList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    private List<CatImage> catImageList;
  
}
