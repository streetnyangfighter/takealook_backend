package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.cat.CatMatchDTO;
import com.snp.takealook.service.cat.CatMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatMatchController {

    private final CatMatchService catMatchService;

    @PostMapping("/catmatch")
    public Long match(@RequestBody CatMatchDTO.Match dto) {
        return catMatchService.match(dto);
    }

    @PatchMapping("/catmatch/accept/{id}")
    public Long accept(@PathVariable Long id) {
        return catMatchService.accept(id);
    }

    @PatchMapping("/catmatch/reject/{id}")
    public Long reject(@PathVariable Long id) {
        return catMatchService.reject(id);
    }

    // 했던 매칭 취소는 보류 상태
    @PatchMapping("/catmatch/cancle/{id}")
    public Long cancle(@PathVariable Long id) {
        return catMatchService.cancle(id);
    }

    // 내 고양이 하나만 그룹에서 빠지기(나만)

}
