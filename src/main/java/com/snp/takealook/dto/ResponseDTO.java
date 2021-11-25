package com.snp.takealook.dto;

import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.UserLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class ResponseDTO {

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

    @Data
    @AllArgsConstructor
    public static class UserLocationListResponse {
        Boolean success;
        String msg;
        List<UserLocation> userLocationList;
    }

    @Data
    @AllArgsConstructor
    public static class NotificationListResponse {
        Boolean success;
        String msg;
        List<Notification> notificationList;
    }
}
