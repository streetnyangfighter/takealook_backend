package com.snp.takealook.dto.user;

import lombok.Data;

public class NotificationDTO {

    @Data
    public static class Get {
        private Long userId;
    }
}
