package com.snp.takealook.config.auth;

import com.snp.takealook.config.auth.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** cors 설정 추가  */
        http.cors().configurationSource(corsConfigurationSource());


        http.csrf().disable();
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
                .logoutSuccessUrl("/").permitAll()

                // OAuth2 설정 진입, userInfoEndpoint() -> 로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
                // userService -> UserService 인터페이스 구현체 등록
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }

    /** cors 설정 추가  */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 URL
        configuration.addAllowedOrigin("http://localhost:3000");
        // 허용할 Header
        configuration.addAllowedHeader("*");
        // 허용할 Http Method
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}