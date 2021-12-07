package com.snp.takealook.api.dto;

import com.snp.takealook.api.domain.cat.*;
import com.snp.takealook.api.domain.user.Notification;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.domain.user.UserLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    @Getter
    public static class UserLocationListResponse {
        private Long id;
        private User user;
        private String sido;
        private String gugun;
        private String dong;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public UserLocationListResponse(UserLocation entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.sido = entity.getSido();
            this.gugun = entity.getGugun();
            this.dong = entity.getDong();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
        }
    }

    /** CAT 관련 ResponseDTO */
    @Getter
    public static class CatResponse {
        private Long id;
        private String name;
        private Byte gender;
        private Byte neutered;
        private CatStatus status;
        private List<User> carers;
        private List<String> infoList;
//        private List<CatImage> catImageList;

        public CatResponse(Cat entity, CatStatus status, List<User> carers, List<String> infoList) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.gender = entity.getGender();
            this.neutered = entity.getNeutered();
            this.status = status;
            this.carers = carers;
            this.infoList = infoList;
        }
    }

    @Getter
    public static class CatListResponse {
        private Long id;
        private User user;
        private CatGroup catGroup;
        private String name;
        private Byte neutered;
        private String info;
        private Boolean dFlag;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatListResponse(Cat entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.catGroup = entity.getCatGroup();
            this.name = entity.getName();
            this.neutered = entity.getNeutered();
            this.info = entity.getInfo();
            this.dFlag = entity.getDflag();
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

    @Getter
    public static class CatStatusResponse {
        private Long id;
        private User user;
        private Byte status;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatStatusResponse(CatStatus entity) {
            this.id = entity.getId();
            this.user = entity.getCat().getUser();
            this.status = entity.getStatus();
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
