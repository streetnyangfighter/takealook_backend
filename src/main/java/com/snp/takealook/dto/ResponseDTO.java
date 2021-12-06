package com.snp.takealook.dto;

import com.snp.takealook.domain.cat.*;
import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ResponseDTO {

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
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private Boolean checked;

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

    @Getter
    public static class UserLocationListResponse {
        private Long id;
        private User user;
        private String sido;
        private String gugun;
        private String dong;

        public UserLocationListResponse(UserLocation entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.sido = entity.getSido();
            this.gugun = entity.getGugun();
            this.dong = entity.getDong();
        }
    }

    @Getter
    public static class CatResponse {
        private Long id;
        private String name;
        private Byte gender;
        private Byte neutered;
        private Byte status;
        private List<User> carers;
//        private List<CatImage> catImageList;

        public CatResponse(Cat cat, List<User> carers) {
            this.id = cat.getId();
            this.name = cat.getName();
            this.gender = cat.getGender();
            this.neutered = cat.getNeutered();
            this.status = cat.getStatus();
            this.carers = carers;
        }
    }

    @Getter
    public static class CatListResponse {
        private Long id;
        private User user;
        private CatGroup catGroup;
        private String name;
        private Byte neutered;
        private Byte status;
        private Boolean dFlag;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatListResponse(Cat entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.catGroup = entity.getCatGroup();
            this.name = entity.getName();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.dFlag = entity.getDFlag();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    @Getter
    public static class CatMatchListResponse {
        private Long id;
        private Cat proposer;
        private Cat accepter;
        private Byte status;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatMatchListResponse(CatMatch entity) {
            this.id = entity.getId();
            this.proposer = entity.getProposer();
            this.accepter = entity.getAccepter();
            this.status = entity.getStatus();
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

        public CatCareListResponse(CatCare entity) {
            this.id = entity.getId();
            this.type = entity.getType();
            this.message = entity.getMessage();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
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
        private Cat cat;
        private String originFileName;
        private String fileName;
        private String contentType;
        private Long fileSize;
        private String filePath;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatImageListResponse(CatImage entity) {
            this.id = entity.getId();
            this.cat = entity.getCat();
            this.originFileName = entity.getOriginFileName();
            this.fileName = entity.getFileName();
            this.contentType = entity.getContentType();
            this.fileSize = entity.getFileSize();
            this.filePath = entity.getFilePath();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }
















































































    @Data
    @AllArgsConstructor
    public static class BaseResponse {
        Boolean success;
        String msg;
    }

    public static class Create extends BaseResponse {
        Long id;

        public Create(Long id, Boolean success, String msg) {
            super(success, msg);
            this.id = id;
        }
    }

    public static class Update extends BaseResponse {
        public Update(Boolean success, String msg) {
            super(success, msg);
        }
    }

    public static class Delete extends BaseResponse {
        public Delete(Boolean success, String msg) {
            super(success, msg);
        }
    }

}
