package com.snp.takealook.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 권한 설정 옵션 시작점
        http.authorizeRequests()
                // 권한 관리 대상 지정, URL, HTTP 메소드별로 관리 가능
                .antMatchers("/**").permitAll()
                //.antMatchers("/cctv/**", "/parkinglot/**", "/parkinglot/**").hasRole(Role.USER.name())

                // 설정된 값들 이외 나머지 URL들은 로그인한 사용자들에게만 노출되도록 설정
                .anyRequest().authenticated()

                // 로그아웃 시, 메인으로 이동
                .and()
                    .logout()
                        .logoutSuccessUrl("/")

                // OAuth2 설정 진입, userInfoEndpoint() -> 로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
                // userService -> UserService 인터페이스 구현체 등록
                .and()
                    .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }
}
