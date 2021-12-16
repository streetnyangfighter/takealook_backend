package com.snp.takealook.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    /*
     * TokenProvider, JwtFilter를 SecurityConfig에 적용
     * */

    private final TokenProvider2 tokenProvider2;

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        JwtFilter jwtFilter = new JwtFilter(tokenProvider2);
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        // addFilterBefore -> 지정된 필터 앞에 커스텀 필터를 추가 (UsernamePasswordAuthenticationFilter보다 먼저 실행)
    }
}
