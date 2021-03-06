package com.snp.takealook.api.dto;

import com.snp.takealook.api.domain.cat.Cat;
import com.snp.takealook.api.domain.cat.CatCare;
import com.snp.takealook.api.domain.cat.CatImage;
import com.snp.takealook.api.domain.cat.CatLocation;
import com.snp.takealook.api.domain.community.Board;
import com.snp.takealook.api.domain.community.Comment;
import com.snp.takealook.api.domain.community.Post;
import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.ProviderType;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.dto.cat.CatDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseDTO {

    /**
     * Flask 통신 후 보내는 ResponseDTO
     */
    @Getter
    @AllArgsConstructor
    public static class AiImgResponse {
        private String dstUrl;
        private String orgUrl;
        private CatDTO.CatPoint catPoint;
    }

    @Getter
    public static class RecommendedCatInfo {
        private Long id;
        private String image;
        private Double leftEarX;
        private Double leftEarY;
        private Double rightEarX;
        private Double rightEarY;
        private Double leftEyeX;
        private Double leftEyeY;
        private Double rightEyeX;
        private Double rightEyeY;

        public RecommendedCatInfo(Cat entity) {
            this.id = entity.getId();
            this.image = entity.getImage();
            this.leftEarX = entity.getLeftEarX();
            this.leftEarY = entity.getLeftEarY();
            this.rightEarX = entity.getLeftEarX();
            this.rightEarY = entity.getRightEarY();
            this.leftEyeX = entity.getLeftEyeX();
            this.leftEyeY = entity.getLeftEyeY();
            this.rightEyeX = entity.getRightEyeX();
            this.rightEyeY = entity.getRightEyeY();
        }
    }

    /**
     * USER 관련 ResponseDTO
     */
    @Getter
    public static class UserResponse {
        private Long id;
        private String email;
        private String nickname;
        private String image;
        private Boolean dflag;
        private ProviderType providerType;

        public UserResponse(User entity) {
            this.id = entity.getId();
            this.email = entity.getEmail();
            this.nickname = entity.getNickname();
            this.image = entity.getImage();
            this.dflag = entity.getDflag();
            this.providerType = entity.getProviderType();
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
        private String dMsg;
        private Boolean aflag;
        private String aMsg;
        private String mainImage;
        private List<UserInfo> carers;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatResponse(Cat entity, List<UserInfo> carers) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.pattern = entity.getPattern();
            this.dflag = entity.getDflag();
            this.dMsg = entity.getDMsg();
            this.aflag = entity.getAflag();
            this.aMsg = entity.getAMsg();
            this.mainImage = entity.getImage();
            this.carers = carers;
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class CatInfoResponse {
        private Long id;
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte pattern;
        private String mainImage;
        private List<CatLocationResponse> catLocations;
        private List<String> userUploadImages;

        public CatInfoResponse(Cat entity, List<CatLocationResponse> catLocations, List<String> userUploadImages) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.pattern = entity.getPattern();
            this.mainImage = entity.getImage();
            this.catLocations = catLocations;
            this.userUploadImages = userUploadImages;
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
    public static class CatRecommendListResponse {
        private Long id;
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private Byte pattern;
        private String mainImage;
        private List<CatLocationResponse> recentLocationList;
        private Long score;

        public CatRecommendListResponse(Cat entity, List<CatLocationResponse> recentLocationList, Long score) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.pattern = entity.getPattern();
            this.mainImage = entity.getImage();
            this.recentLocationList = recentLocationList;
            this.score = score;
        }
    }


    @Getter
    public static class CatStarResponse {
        private Long id;
        private String name;
        private String dMsg;
        private String mainImage;

        public CatStarResponse(Cat entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.dMsg = entity.getDMsg();
            this.mainImage = entity.getImage();
        }
    }

    @Getter
    public static class CatAdoptedResponse {
        private Long id;
        private String name;
        private String aMsg;
        private String mainImage;

        public CatAdoptedResponse(Cat entity) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.aMsg = entity.getAMsg();
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
        private String userName;
        private String path;

        public CatImageListResponse(CatImage entity) {
            this.id = entity.getId();
            this.userName = entity.getSelection().getUser().getNickname();
            this.path = entity.getPath();
        }
    }


    /** COMMUNITY 관련 ResponseDTO */
    @Getter
    public static class PostResponse {
        private Board board;
        private Long postId;
        private UserInfo writer;
        private String thumbnail;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Boolean checkLike;
        private int postLike;
        private List<CommentResponse> commentList;
        private int commentListCount;

        public PostResponse(Post entity) {
            this.board = entity.getBoard();
            this.postId = entity.getId();
            this.writer = new UserInfo(entity.getWriter());
            this.thumbnail = entity.getImgUrl();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.checkLike = entity.getCheckLike();
            this.postLike = entity.getPostLikeList().size();
            this.commentList = entity.getCommentList().stream().map(CommentResponse::new).collect(Collectors.toList());
            this.commentListCount = commentList.size();
        }
    }

    @Getter
    public static class CommentResponse {
        private UserInfo writer;
        private Long commentId;
        private String content;
        private LocalDateTime modifiedAt;
        private int commentLike;

        public CommentResponse(Comment entity) {
            this.writer = new UserInfo(entity.getWriter());
            this.commentId = entity.getId();
            this.content = entity.getContent();
            this.modifiedAt = entity.getModifiedAt();
            this.commentLike = entity.getCommentLikeList().size();
        }
    }

}
