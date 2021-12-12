package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.cat.SelectionService;
import com.snp.takealook.api.service.cat.CatService;
import com.snp.takealook.api.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class SelectionController {

    private final SelectionService selectionService;
    private final CatService catService;
    private final NotificationService notificationService;

    @PostMapping("/user/{userId}/cat/{catId}/selection")
    public Long save(@PathVariable Long userId, @PathVariable Long catId) {
        Long saveId = selectionService.save(userId, catId);
        notificationService.catSave(userId, catId, (byte) 2);
        return saveId;
    }

    //간택 정보 수정 - 기존 고양이 간택 + 자신이 작성한 정보 함께 이동
    @PatchMapping("/user/{userId}/cat/{catId}/selection")
    public Long update(@PathVariable Long userId, @PathVariable Long catId, Long newCatId) {
        Long updateId = selectionService.update(userId, catId, newCatId);
        notificationService.catSave(userId, catId, (byte) 3);
        notificationService.catSave(userId, newCatId, (byte) 2);
        catService.delete(catId);

        return updateId;
    }

    //간택 정보 수정 - 새로운 고양이 저장/간택 + 자신이 작성한 정보 함께 이동
    @PatchMapping("/user/{userId}/cat/{catId}/selection/new")
    public Long updateNewCat(@PathVariable Long userId, @PathVariable Long catId, @RequestBody CatDTO.Create catInfo) {
        Long newCatId = catService.save(catInfo);
        notificationService.catSave(userId, catId, (byte) 4);
        return selectionService.update(userId, catId, newCatId);
    }

    //간택 삭제(내가 작성한 정보 남김)
    @PatchMapping("/user/{userId}/cat/{catId}/selection/soft-delete")
    public void softDelete(@PathVariable Long userId, @PathVariable Long catId, boolean deleteCat) {
        selectionService.softDelete(userId, catId);
        notificationService.catSave(userId, catId, (byte) 4);
    }

}
