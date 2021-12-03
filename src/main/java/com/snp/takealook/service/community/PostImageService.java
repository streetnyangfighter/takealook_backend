package com.snp.takealook.service.community;

import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.community.PostImage;
import com.snp.takealook.repository.community.PostImageRepository;
import com.snp.takealook.repository.community.PostRepository;
import com.snp.takealook.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(Long postId, MultipartFile[] files) throws IOException, NoSuchAlgorithmException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + " is not valid"));

        if (files.length != 0) {
            for (MultipartFile file : files) {
                String orignalFileName = file.getOriginalFilename();
                String fileName = new MD5Generator(orignalFileName).toString();
                String contentType = file.getContentType();
                Long fileSize = file.getSize();
                String savePath = System.getProperty("user.dir") + "/postImages";

                if (!new File(savePath).exists()) {
                    try {
                        new File(savePath).mkdir();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                String filePath = savePath + "/" + fileName;
                file.transferTo(new File(filePath));

                PostImage postImage = PostImage.builder()
                        .post(post)
                        .orignFileName(orignalFileName)
                        .contentType(contentType)
                        .filePath(filePath)
                        .fileSize(fileSize)
                        .build();

                System.out.println(postImage.getPost().getTitle());
                postImageRepository.save(postImage);

            }
        }
        return post.getId();
    }
}
