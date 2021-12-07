package com.snp.takealook.dto.user;

import com.snp.takealook.domain.user.Notification;
import com.snp.takealook.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class NotificationDTO {

    @Getter
    public static class Create {
        private String message;
        private Byte type;

        @Builder
        public Create(String message, Byte type) {
            this.message = message;
            this.type = type;
        }

        public Notification toEntity(User user) {
            return Notification.builder()
                    .user(user)
                    .message(message)
                    .type(type)
                    .build();
        }
    }
}
