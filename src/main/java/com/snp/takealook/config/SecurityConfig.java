package com.snp.takealook.config;

import com.snp.takealook.config.jwt.JwtAccessDeniedHandler;
import com.snp.takealook.config.jwt.JwtAuthenticationEntryPoint;
import com.snp.takealook.config.jwt.JwtSecurityConfig;
import com.snp.takealook.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

//    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** cors 설정 추가  */
        http.cors().configurationSource(corsConfigurationSource());

        http
                // 개발 편의성을 위해 CSRF 프로텍션을 비활성화
                .csrf()
                .disable()
                // HTTP 기본 인증 비활성화
                .httpBasic()
                .disable()
                // 폼 기반 인증 비활성화
                .formLogin()
                .disable()
                // stateless한 세션 정책 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));


//        http.csrf().disable();
//        // 권한 설정 옵션 시작점
//        http.authorizeRequests()
//                // 권한 관리 대상 지정, URL, HTTP 메소드별로 관리 가능
//                .antMatchers("/**").permitAll()
//
//                // 설정된 값들 이외 나머지 URL들은 로그인한 사용자들에게만 노출되도록 설정
//                .anyRequest().authenticated()
//
//                // 로그아웃 시, 메인으로 이동
//                .and()
//                .logout()
//                .logoutSuccessUrl("/").permitAll();
//
//                // OAuth2 설정 진입, userInfoEndpoint() -> 로그인 성공 이후 사용자 정보 가져올 때의 설정 담당
//                // userService -> UserService 인터페이스 구현체 등록
////                .and()
////                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
    }

    /** cors 설정 추가  */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 URL
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("http://localhost:5500");
        // 허용할 Header
        configuration.addAllowedHeader("*");
        // 허용할 Http Method
        configuration.addAllowedMethod("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}