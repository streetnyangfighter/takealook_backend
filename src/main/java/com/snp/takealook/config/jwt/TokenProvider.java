package com.snp.takealook.config.jwt;

import com.snp.takealook.api.domain.user.User;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;

@Component
public class TokenProvider {

    private Key key;

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
