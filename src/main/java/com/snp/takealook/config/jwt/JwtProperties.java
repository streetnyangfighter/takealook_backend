package com.snp.takealook.config.jwt;

public interface JwtProperties {

    String SECRET = "java";
    Integer EXPIRE_TIME = 1000 * 60 * 60 * 24; // 하루
    String TOKEN_PRIFIX = "Bearer ";
    String TOKEN_HAEDER = "Authorization";

}
