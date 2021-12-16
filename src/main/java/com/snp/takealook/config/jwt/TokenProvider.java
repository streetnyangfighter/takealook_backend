package com.snp.takealook.config.jwt;

import com.snp.takealook.api.domain.user.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class TokenProvider {

    private final String secret;
    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(User userEntity) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(3).toMillis()))
                .claim("userId", userEntity.getId())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
