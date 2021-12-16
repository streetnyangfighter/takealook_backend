package com.snp.takealook.config.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenProvider2 tokenProvider2;

    /*
     * 토큰의 인증정보를 Security Context(Thread Local)에 저장
     * */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // bearer 토큰 받음
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        // 토큰이 있고, claims가 있다면
        if(StringUtils.hasText(jwt) && tokenProvider2.validateToken(jwt)){
            Authentication authentication = tokenProvider2.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("SecurityContext - {} save Authentication, URI = {}",authentication.getName(),requestURI);
        }else{

            log.debug("There is no Valid Valid JWT Token. URI = {}",requestURI);
        }

        chain.doFilter(request, response);
    }
    /*
     * requestHeader에서 토큰정보를 가져온다.
     * */
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
