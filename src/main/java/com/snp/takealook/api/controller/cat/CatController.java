package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.dto.cat.CatDTO;
import com.snp.takealook.api.service.SelectionService;
import com.snp.takealook.api.service.cat.CatImageService;
import com.snp.takealook.api.service.cat.CatLocationService;
import com.snp.takealook.api.service.cat.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;
    private final SelectionService selectionService;
    private final CatLocationService catLocationService;
    private final CatImageService catImageService;

    @PostMapping(value = "/user/{userId}/cat/selection", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long saveNewCat(@PathVariable Long userId,
                           @RequestPart(value = "catInfo") CatDTO.Create catInfo,
                           @RequestPart(value = "catLoc") CatDTO.LocationList[] catLocList,
                           @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        Long catId = catService.save(catInfo);
        Long selectionId = selectionService.save(userId, catId);
        catLocationService.saveAll(selectionId, catLocList);
        catImageService.save(selectionId, files);

        return catId;
    }

    @PostMapping("/user/{userId}/cat/{catId}/match")
    public Long selectCat(@PathVariable Long userId, @PathVariable Long catId) {
        return selectionService.save(userId, catId);
    }

    @PostMapping(value = "/user/{userId}/cat/{catId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long update(@PathVariable Long userId,
                       @PathVariable Long catId,
                       @RequestPart(value = "catInfo", required = false) CatDTO.Update catInfo,
                       @RequestPart(value = "catLoc", required = false) CatDTO.LocationList[] catLocList,
                       @RequestPart(value = "catImg", required = false) List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        catService.update(userId, catId, catInfo);
        //save로 update로? save하면 계속 누적, update하면 이전 정보 휘발
        catLocationService.update(userId, catId, catLocList);
        catImageService.update(userId, catId, files);

        return catId;
    }

    @PatchMapping("/user/{userId}/cat/{catId}")
    public Long changeStatus(@PathVariable Long userId, @PathVariable Long catId, Byte status) {
        return catService.changeStatus(userId, catId, status);
    }

    // 간택 취소 보류

    // 고양이 상세 조회 보류(함께 돌보는 사람들, 이미지 함께?)
    @GetMapping("user/{userId}/cat/{catId}")
    public ResponseDTO.CatResponse findOne(@PathVariable Long userId, @PathVariable Long catId) {
        return catService.findOne(userId, catId);
    }

    @GetMapping("user/{userId}/cats")
    public List<ResponseDTO.CatListResponse> findAllByUserId(@PathVariable Long userId) {
        return catService.findAllByUserId(userId);
    }

    // 고양이별 발견 지역 전체 조회

    // 고양이별 이미지 전체 조회 -> 프론트에 보내줘야 할 값 정확히 확인
}
