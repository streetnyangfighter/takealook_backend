package com.snp.takealook.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMilliseconds;

    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }
    //Bean secret 키 base64로 변환
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes); // Key 객체로 변환
    }

    // Authentication 객체에 포함되어 있는 권한 정보들을 담은 토큰을 생성하고 만료 시간 지정
    public String createToken(Authentication authentication) {
        System.out.println("--------------------- createToken");
        System.out.println(authentication.toString()); //UsernamePasswordAuthenticationToken [Principal=4, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[USER]]
        System.out.println(authentication.getCredentials()); // null
        System.out.println(authentication.getPrincipal()); // 4
        System.out.println(authentication.getDetails()); // null
        System.out.println(authentication.getAuthorities()); // [User]
        System.out.println(authentication.getName()); // 4, 이거... 유저idx 인듯

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        System.out.println(authorities); // User
        System.out.println("createToken");
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName()) // payload : "sub" : "4"
                .claim(AUTHORITIES_KEY, authorities) // payload
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact(); // 토큰 생성
    }

    // 토큰에 담겨있는 권한 정보들을 이용해 Authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰 검증하는 역할 수행
    public boolean validateToken(String token) {
        try {
            // 토큰의 claims get
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
