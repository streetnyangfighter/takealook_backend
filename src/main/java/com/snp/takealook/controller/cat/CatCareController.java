package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatCareDTO;
import com.snp.takealook.service.cat.CatCareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatCareController {

    private final CatCareService catCareService;

    @PostMapping("/user/{userId}/cat/{catId}/catcare")
    public Long save(@PathVariable Long catId, @RequestBody CatCareDTO.Create dto) {
        return catCareService.save(catId, dto);
    }

    @PutMapping("/user/{userId}/cat/{catId}/catcare/{id}")
    public Long update(@PathVariable Long id, @RequestBody CatCareDTO.Update dto) {
        return catCareService.update(id, dto);
    }

    @DeleteMapping("/user/{userId}/cat/{catId}/catcare/{id}")
    public Long delete(@PathVariable Long id) {
        return catCareService.delete(id);
    }

    @GetMapping("/user/{userId}/cat/{catId}/catcares")
    public List<ResponseDTO.CatCareListResponse> findAllByCatId(@PathVariable Long catId) {
        return catCareService.findAllByCatId(catId);
    }
}
