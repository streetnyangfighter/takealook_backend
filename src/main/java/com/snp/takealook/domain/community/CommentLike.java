package com.snp.takealook.domain.community;

import com.snp.takealook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}