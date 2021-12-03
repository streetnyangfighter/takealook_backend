package com.snp.takealook.service.community;

import com.snp.takealook.domain.community.Post;
import com.snp.takealook.domain.community.PostImage;
import com.snp.takealook.repository.community.PostImageRepository;
import com.snp.takealook.repository.community.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long save(Long postId, MultipartFile file) throws IOException, NoSuchAlgorithmException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id: " + postId + " is not valid"));

        List<PostImage> oldPostImages = post.getPostImageList();

        for (PostImage oldPostImage :oldPostImages) {
            File oldFile = new File(oldPostImage.getFilePath());

            if(oldFile.exists()) {
                oldFile.delete();
            } else {
                System.out.println("not exists");
            }
        }

        postImageRepository.deleteAll(oldPostImages);
        post.getPostImageList().removeAll(oldPostImages);

        if (!file.isEmpty()) {
            String orignalFileName = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long fileSize = file.getSize();
            String savePath = System.getProperty("user.dir") + "/images/postImages/" + post.getId();

            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            String filePath = savePath + "/" + orignalFileName;
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
        return post.getId();
    }
}
