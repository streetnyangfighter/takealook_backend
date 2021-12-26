package com.snp.takealook.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.config.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final HttpSession session;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, HttpSession session) {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.session = session;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("권한이 필요한 요청이 들어옴");

        String header = request.getHeader(JwtProperties.TOKEN_HAEDER);

        log.info("token => {} ", header);

        if(header == null || !header.startsWith(JwtProperties.TOKEN_PRIFIX)){
            chain.doFilter(request,response);
            return;
        }

        String token = header.replace(JwtProperties.TOKEN_PRIFIX, "");

        Long userId = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build().verify(token)
                .getClaim("id").asLong();

        log.info("userId => {}",userId);

        if(userId != null){

            User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유효하지 않은 User 입니다."));
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            session.setAttribute("principal", principalDetails);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails,
                            principalDetails.getPassword(),
                            principalDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication); //인증 완료 =
        }

        chain.doFilter(request, response);
    }
}
