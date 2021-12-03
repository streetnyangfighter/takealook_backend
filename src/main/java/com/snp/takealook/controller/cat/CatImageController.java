package com.snp.takealook.controller.cat;

import com.snp.takealook.service.cat.CatImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class CatImageController {

    private final CatImageService catImageService;

    @PostMapping("/catimage/{catId}")
    public Long save(@PathVariable Long catId, @RequestPart(value = "files") MultipartFile[] files) throws IOException, NoSuchAlgorithmException {
        // 아니 이거 예외 다 throw 해도 되는걸까? 흠;
        return catImageService.save(catId, files);
    }

    //테스트용 코드
    @PostMapping("/test")
    public Integer save(@RequestPart(value = "files", required = false) MultipartFile[] files) {
        return files.length;
    }
}
