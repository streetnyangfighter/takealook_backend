package com.snp.takealook.config.jwt;

public interface JwtProperties {

    String SECRET = "java";
    Integer EXPIRE_TIME = 1000 * 60 * 60; //1시간
    String TOKEN_PRIFIX = "Bearer ";
    String TOKEN_HAEDER = "Authorization";

}
