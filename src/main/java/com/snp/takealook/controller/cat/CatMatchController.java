package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatMatchDTO;
import com.snp.takealook.service.cat.CatMatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/catmatch/send/{userId}")
    public List<ResponseDTO.CatMatchListResponse> findAllSendByUserId(@PathVariable Long userId) {
        return catMatchService.findAllSendByUserId(userId);
    }

    @GetMapping("/catmatch/receive/{userId}")
    public List<ResponseDTO.CatMatchListResponse> findAllReceiveByUserId(@PathVariable Long userId) {
        return catMatchService.findAllReceiveByUserId(userId);
    }

}
