package com.snp.takealook.dto;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatGroup;
import com.snp.takealook.domain.cat.CatImage;
import com.snp.takealook.domain.cat.CatMatch;
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
