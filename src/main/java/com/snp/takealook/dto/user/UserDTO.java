package com.snp.takealook.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    public static class Get{
        private Long id;
    }
}
