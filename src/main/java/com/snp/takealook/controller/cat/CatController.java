package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatDTO;
import com.snp.takealook.service.cat.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;

    @PostMapping("/cat")
    public Long save(@RequestBody CatDTO.Create dto) {
        return catService.save(dto);
    }

    @PutMapping("/cat/info/{id}")
    public Long updateInfo(@PathVariable Long id, @RequestBody CatDTO.Update dto) {
        return catService.updateInfo(id, dto);
    }

    @PutMapping("/cat/locations/{id}")
    public Long updateLocations(@PathVariable Long id, @RequestBody List<CatDTO.LocationList> dtoList) {
        return catService.updateLocations(id, dtoList);
    }

    @PatchMapping("/cat/status/{id}")
    public Long updateStatus(@PathVariable Long id, @RequestBody CatDTO.Update dto) {
        return catService.updateStatus(id, dto);
    }

    @PatchMapping("/cat/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return catService.delete(id);
    }

    @PatchMapping("/cat/restore/{id}")
    public Long restore(@PathVariable Long id) {
        return catService.restore(id);
    }

    @PatchMapping("/cat/groupout/{id}")
    public Long removeFromGroup(@PathVariable Long id) {
        return catService.removeFromGroup(id);
    }

    @GetMapping("/cat/{userId}")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@PathVariable Long userId) {
        return catService.findAllByUserId(userId);
    }

}
