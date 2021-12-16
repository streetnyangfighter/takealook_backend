package com.snp.takealook.api.dto;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.domain.community.Board;
import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        private String message;
        private Byte type;
        private Boolean checked;
        private Long linkedId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public NotificationListResponse(Notification entity) {
            this.id = entity.getId();
            this.message = entity.getMessage();
            this.type = entity.getType();
            this.checked = entity.getChecked();
            this.linkedId = entity.getLinkedId();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class UserInfo {
        private Long id;
        private String userName;
        private String userImage;
        private Boolean dflag;

        public UserInfo(User entity) {
            this.id = entity.getId();
            this.userName = entity.getNickname();
            this.userImage = entity.getImage();
            this.dflag = entity.getDflag();
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
        private Boolean dflag;
//        private String dMsg;
        private Boolean aflag;
//        private String aMsg;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private List<UserInfo> carers;
        private String mainImage;

        public CatResponse(Cat entity, List<UserInfo> carers) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.pattern = entity.getPattern();
            this.dflag = entity.getDflag();
//            this.dMsg = entity.getDMsg();
            this.aflag = entity.getAflag();
//            this.aMsg = entity.getAMsg();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.carers = carers;
            this.mainImage = entity.getImage();
        }
    }

    @Getter
    public static class CatListResponse {
        private Long id;
        private String name;
        private Byte status;
        private List<CatCareListResponse> recentCares;
        private CatLocationResponse recentLocation;
        private String mainImage;

        public CatListResponse(Cat entity, List<CatCareListResponse> recentCares) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.status = entity.getStatus();
            this.recentCares = recentCares;
            this.mainImage = entity.getImage();
        }

        public CatListResponse(Cat entity, CatLocationResponse recentLocation) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.status = entity.getStatus();
            this.recentLocation = recentLocation;
            this.mainImage = entity.getImage();
        }
    }

    @Getter
    public static class CatStarResponse {
        private Long id;
        private String name;
//        private String dMsg;
        private String mainImage;

        public CatStarResponse(Cat entity) {
            this.id = entity.getId();
            this.name = entity.getName();
//            this.dMsg = entity.getDMsg();
            this.mainImage = entity.getImage();
        }
    }

    @Getter
    public static class CatAdoptedResponse {
        private Long id;
        private String name;
//        private String aMsg;
        private String mainImage;

        public CatAdoptedResponse(Cat entity) {
            this.id = entity.getId();
            this.name = entity.getName();
//            this.aMsg = entity.getAMsg();
            this.mainImage = entity.getImage();
        }
    }

    @Getter
    public static class CatCareListResponse {
        private Long id;
        private Byte type;
        private String message;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private UserInfo carer;

        public CatCareListResponse(CatCare entity) {
            this.id = entity.getId();
            this.type = entity.getType();
            this.message = entity.getMessage();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.carer = new UserInfo(entity.getSelection().getUser());
        }
    }

    @Getter
    public static class CatLocationResponse {
        private Long id;
        private Double latitude;
        private Double longitude;
        private LocalDateTime createdAt;

        public CatLocationResponse(CatLocation entity) {
            this.id = entity.getId();
            this.latitude = entity.getLatitude();
            this.longitude = entity.getLongitude();
            this.createdAt = entity.getCreatedAt();
        }
    }

    @Getter
    public static class CatImageListResponse {
        private Long id;
        private String userNamae;
        private String path;

        public CatImageListResponse(CatImage entity) {
            this.id = entity.getId();
            this.userNamae = entity.getSelection().getUser().getNickname();
            this.path = entity.getPath();
        }
    }



    /** COMMUNITY 관련 ResponseDTO */
    @Getter
    public static class PostResponse {
        private Board board;
        private UserInfo writer;
        private String thumbnail;
        private String title;
        private String content;
        private LocalDateTime modifiedAt;
        private int postLike;
        private List<CommentResponse> commentList;
        private int commentListCount;

        public PostResponse(Post entity) {
            this.board = entity.getBoard();
            this.writer = new UserInfo(entity.getWriter());
            this.thumbnail = entity.getImgUrl();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
            this.postLike = entity.getPostLikeList().size();
            this.commentList = entity.getCommentList().stream().map(CommentResponse::new).collect(Collectors.toList());
            this.commentListCount = commentList.size();
        }
    }

//    @Getter
//    public static class PostListResponse {
//        private Board board;
//        private UserInfo writer;
//        private String title;
//        private String content;
//        private int postLike;
//        private LocalDateTime modifiedAt;
//        private int commentListCount;
//
//        public PostListResponse(Post entity) {
//            this.board = entity.getBoard();
//            this.writer = new UserInfo(entity.getWriter());
//            this.title = entity.getTitle();
//            this.content = entity.getContent();
//            this.postLike = entity.getPostLikeList().size();
//            this.modifiedAt = entity.getModifiedAt();
//            this.commentListCount = entity.getCommentList().size();
//        }
//    }

    @Getter
    public static class CommentResponse {
        private UserInfo writer;
        private String content;
        private LocalDateTime modifiedAt;
        private int commentLike;

        public CommentResponse(Comment entity) {
            this.writer = new UserInfo(entity.getWriter());
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
            this.commentLike = entity.getCommentLikeList().size();
        }
    }

}
