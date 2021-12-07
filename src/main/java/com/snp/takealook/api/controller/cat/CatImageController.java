package com.snp.takealook.api.controller.cat;

import com.snp.takealook.api.dto.ResponseDTO;
import com.snp.takealook.api.service.cat.CatImageService;
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
public class CatImageController {

    private final CatImageService catImageService;

    @PostMapping(value = "cat/{catId}/catimage", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Long save(@PathVariable Long catId, @RequestPart(value = "files") List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        // 아니 이거 예외 다 throw 해도 되는걸까? 흠;
        return catImageService.save(catId, files);
    }

    // 기능 테스트용
    @PutMapping("/cat/{catId}/catimage")
    public Long update(@PathVariable Long catId, @RequestBody List<MultipartFile> files) throws IOException, NoSuchAlgorithmException {
        return catImageService.update(catId, files);
    }

    @GetMapping("/cat/{catId}/catimages")
    public List<ResponseDTO.CatImageListResponse> findAllByCatId(@PathVariable Long catId) {
        return catImageService.findAllByCatId(catId);
    }
}
