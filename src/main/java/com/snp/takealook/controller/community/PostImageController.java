package com.snp.takealook.controller.community;

import com.snp.takealook.service.community.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
public class PostImageController {

    private final PostImageService postImageService;

    @PostMapping("/post/{postId}/thumbnail")
    public Long save(@PathVariable Long postId, @RequestPart(value = "files") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return postImageService.save(postId, file);
    }
}
