package com.snp.takealook.dto;

import com.snp.takealook.domain.cat.Cat;
import com.snp.takealook.domain.cat.CatGroup;
import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.User;
import com.snp.takealook.domain.user.UserLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

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
        private byte type;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private boolean checked;

        public NotificationListResponse(Notification entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.message = entity.getMessage();
            this.type = entity.getType();
            this.createdAt = entity.getCreatedAt();
            this.modifiedAt = entity.getModifiedAt();
            this.checked = entity.isChecked();
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
    public static class CatListResponse {
        private Long id;
        private User user;
        private CatGroup catGroup;
        private String name;
        private byte neutered;
        private byte status;
        private boolean dFlag;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public CatListResponse(Cat entity) {
            this.id = entity.getId();
            this.user = entity.getUser();
            this.catGroup = entity.getCatGroup();
            this.name = entity.getName();
            this.neutered = entity.getNeutered();
            this.status = entity.getStatus();
            this.dFlag = entity.isDFlag();
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
