package com.snp.takealook.domain.cat;

import com.snp.takealook.domain.user.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Cat {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    private User user;

    @NonNull
    @OneToOne
    private CatGroup catGroup;

    @NonNull
    private String name;

    @NonNull
    private byte neutered;

    @NonNull
    private byte status;

    @NonNull
    private byte dFlag;

    @NonNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NonNull
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    private List<CatCare> catCareList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    private List<CatLocation> catLocationList;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL)
    private List<CatImage> catImageList;
  
}
