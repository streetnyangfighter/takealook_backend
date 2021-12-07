package com.snp.takealook.controller.cat;

import com.snp.takealook.dto.ResponseDTO;
import com.snp.takealook.dto.cat.CatMatchDTO;
import com.snp.takealook.service.cat.CatMatchService;
import com.snp.takealook.service.user.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatMatchController {

    private final CatMatchService catMatchService;
    private final NotificationService notificationService;

    @PostMapping("user/{userId}/cat/{catId}/catmatch")
    public Long match(@RequestBody CatMatchDTO.Match dto) {
        Long saveId = catMatchService.match(dto);
        notificationService.saveMatchNotification(saveId, null, (byte)3);
        return saveId;
    }

    @PatchMapping("user/{userId}/cat/{catId}/catmatch/accept/{catmatchId}")
    public Long accept(@PathVariable Long userId, @PathVariable Long catId, @PathVariable Long catmatchId) {
        catMatchService.accept(catmatchId);
        notificationService.saveMatchNotification(catmatchId, (byte)1, (byte)4);
        notificationService.saveGroupNotification(userId, catId, (byte)5);
        return catmatchId;
    }

    @PatchMapping("user/{userId}/cat/{catId}/catmatch/reject/{catmatchId}")
    public Long reject(@PathVariable Long catmatchId) {
        catMatchService.reject(catmatchId);
        notificationService.saveMatchNotification(catmatchId, (byte)2, (byte)4);
        return catmatchId;
    }

    @DeleteMapping("/user/{userId}/catmatch/receive/{catmatchId}")
    public void delete(@PathVariable Long catmatchId) {
        catMatchService.delete(catmatchId);
    }

    @GetMapping("/user/{userId}/catmatch/send")
    public List<ResponseDTO.CatMatchListResponse> findAllSendByUserId(@PathVariable Long userId) {
        return catMatchService.findAllSendByUserId(userId);
    }

    @GetMapping("/user/{userId}/catmatch/receive")
    public List<ResponseDTO.CatMatchListResponse> findAllReceiveByUserId(@PathVariable Long userId) {
        return catMatchService.findAllReceiveByUserId(userId);
    }

}
