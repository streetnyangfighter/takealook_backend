package com.snp.takealook.api.dto;

import com.snp.takealook.api.domain.cat.*;
import com.snp.takealook.api.domain.community.Board;
import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ResponseDTO {

    /** USER 관련 ResponseDTO */
    @Getter
    public static class UserResponse {
        private Long id;
        private String loginId;
        private String nickname;
        private String phone;
        private String image;
        private String loginType;

        public UserResponse(User entity) {
            this.id = entity.getId();
            this.loginId = entity.getLoginId();
            this.nickname = entity.getNickname();
            this.phone = entity.getPhone();
            this.image = entity.getImage();
            this.loginType = entity.getLoginType();
        }
    }

    @Getter
    public static class NotificationListResponse {
        private Long id;
        private User user;
        private String message;
        private Byte type;
        private Boolean checked;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public NotificationListResponse(Notification entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.message = entity.getMessage();
            this.type = entity.getType();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.checked = entity.getChecked();
        }
    }

    /** CAT 관련 ResponseDTO */
    @Getter
    public static class CatResponse {
        private Long id;
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private Byte pattern;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Map<String, String> carers;  // 같이 돌보는 사람 이름과 프로필 사진 url
//        private List<User> carers;
//        private List<CatImage> catImageList;
//        지금은 아래 단일 DTO랑 중복되지만 위의 내용 추가되면 중복 X

        public CatResponse(Cat entity, Map<String, String> carers) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.pattern = entity.getPattern();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.carers = carers;
//            this.carers = carers;
//            this.catImageList = catImageList;
        }
    }

    @Getter
    public static class CatListResponse {
        private Long id;
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private Byte pattern;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
//        회원의 고양이 리스트 뽑아줄 때,
//        고양이 대표 사진 1장
//        돌봐주고 있는 사람 수? 같은 정보 필요하다면 추가

        public CatListResponse(Cat entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.pattern = entity.getPattern();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class CatCareListResponse {
        private Long id;
        private Byte type;
        private String message;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private String userName;

        public CatCareListResponse(CatCare entity) {
            this.id = entity.getId();
            this.type = entity.getType();
            this.message = entity.getMessage();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.userName = entity.getSelection().getUser().getNickname();
        }
    }

    @Getter
    public static class CatLocationListResponse {
        private Long id;
        private Double latitude;
        private Double longtitude;
        private LocalDateTime createdAt;

        public CatLocationListResponse(CatLocation entity) {
            this.id = entity.getId();
            this.latitude = entity.getLatitude();
            this.longtitude = entity.getLongitude();
            this.createdAt = entity.getCreatedAt();
        }
    }

    @Getter
    public static class CatImageListResponse {
        private Long id;
        private String originFileName;
        private String fileName;
        private String contentType;
        private Long fileSize;
        private String filePath;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatImageListResponse(CatImage entity) {
            this.id = entity.getId();
            this.originFileName = entity.getOriginFileName();
            this.fileName = entity.getFileName();
            this.contentType = entity.getContentType();
            this.fileSize = entity.getFileSize();
            this.filePath = entity.getFilePath();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }


    /** COMMUNITY 관련 ResponseDTO */
    @Getter
    public static class PostResponse {
        private Board board;
        private String writer;
        private String title;
        private String content;
        private LocalDateTime modifiedAt;
        // 댓글, 추천

        public PostResponse(Post entity) {
            this.board = entity.getBoard();
            this.writer = entity.getWriter().getNickname();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class PostListResponse {
        private Board board;
        private User writer;
        private String title;
        private String content;
        private LocalDateTime modifiedAt;
        // 댓글, 추천

        public PostListResponse(Post entity) {
            this.board = entity.getBoard();
            this.writer = entity.getWriter();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class CommentResponse {
        private Post post;
        private User writer;
        private String content;
        private LocalDateTime modifiedAt;

        public CommentResponse(Comment entity) {
            this.post = entity.getPost();
            this.writer = entity.getWriter();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

}
