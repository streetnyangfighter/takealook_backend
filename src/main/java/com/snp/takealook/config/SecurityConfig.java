package com.snp.takealook.config;

import com.snp.takealook.config.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider2 tokenProvider2;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    /** cors 설정 추가  */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:5500");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader(JwtProperties.TOKEN_HAEDER);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** cors 설정 추가  */
        http.cors().configurationSource(corsConfigurationSource());

        http
                .csrf().disable() // 개발 편의성을 위해 CSRF 프로텍션을 비활성화
                .headers().frameOptions().disable()
                .and()
                .httpBasic().disable() // HTTP 기본 인증 비활성화
                .formLogin().disable() // 폼 기반 인증 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // stateless한 세션 정책 설정

                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()



                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 401 UnAuthorized 처리 (인증 실패)
                .accessDeniedHandler(jwtAccessDeniedHandler) // 403 Forbidden 처리 (권한 불충분)
                .and()
                .apply(new JwtSecurityConfig(tokenProvider2))

                .and()
                .logout().logoutUrl("/").permitAll()

                .and()
                .oauth2Login().userInfoEndpoint()

        ;

//                // OAuth2 설정 진입, userInfoEndpoint() -> 로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
//                // userService -> UserService 인터페이스 구현체 등록
////                .and()
////                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }
}