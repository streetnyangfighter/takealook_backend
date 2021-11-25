package com.snp.takealook.dto.user;


import lombok.Data;

public class UserLocationDTO {

    @Data
    public static class Create {
        private Long userId;
        private String sido;
        private String gugun;
        private String dong;
    }

    @Data
    public static class Delete {
        private Long id;
    }

    @Data
    public static class Get {
        private Long userId;
    }
}
