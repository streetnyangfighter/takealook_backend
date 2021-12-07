package com.snp.takealook.api.repository.community;

import com.snp.takealook.api.domain.community.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
