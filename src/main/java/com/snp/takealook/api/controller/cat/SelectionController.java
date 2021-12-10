package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.SelectionService;
import com.snp.takealook.api.service.cat.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class SelectionController {

    private final SelectionService selectionService;
    private final CatService catService;

    @PostMapping("/user/{userId}/cat/{catId}/selection")
    public Long save(@PathVariable Long userId, @PathVariable Long catId) {
        return selectionService.save(userId, catId);
    }

    //간택 정보 수정 - 기존 고양이 간택 + 자신이 작성한 정보 함께 이동
    @PatchMapping("/user/{userId}/cat/{catId}/selection")
    public Long update(@PathVariable Long userId, @PathVariable Long catId, Long newCatId) {
        return selectionService.update(userId, catId, newCatId);
    }

    //간택 정보 수정 - 새로운 고양이 저장/간택 + 자신이 작성한 정보 함께 이동
    @PatchMapping("/user/{userId}/cat/{catId}/selection/new")
    public Long updateNewCat(@PathVariable Long userId, @PathVariable Long catId, @RequestBody CatDTO.Create catInfo) {
        Long newCatId = catService.save(catInfo);
        return selectionService.update(userId, catId, newCatId);
    }

    //간택 삭제(내가 작성한 정보 남김)
    @PatchMapping("/user/{userId}/cat/{catId}/selection/soft-delete")
    public void softDelete(@PathVariable Long userId, @PathVariable Long catId) {
        selectionService.softDelete(userId, catId);
    }

    //간택 삭제(내가 작성한 정보 남기지 않음)
    @DeleteMapping("/user/{userId}/cat/{catId}/selection/hard-delete")
    public void hardDelete(@PathVariable Long userId, @PathVariable Long catId) {
        selectionService.hardDelete(userId, catId);
    }
}
