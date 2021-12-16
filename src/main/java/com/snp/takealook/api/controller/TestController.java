package com.snp.takealook.api.controller;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.api.service.S3Uploader;
import com.snp.takealook.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final S3Uploader s3Uploader;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/images")
    public String upload(@RequestPart(value = "images") MultipartFile multipartFile) throws IOException {
        String url = s3Uploader.upload(multipartFile, "static");
        System.out.println(url);
        return "test";
    }

    @GetMapping("/test/{id}")
    public String createToken(@PathVariable Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저 ID가 없습니다."));

        return tokenProvider.createToken(user);
    }
}
