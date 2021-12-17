package com.snp.takealook.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.user.UserRepository;
import com.snp.takealook.config.auth.PrincipalDetails;
import com.snp.takealook.config.jwt.JwtProperties;
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

        log.info("시큐리티 사용하면, 권한이 필요한 요청이 들어옴");

        String header = request.getHeader(JwtProperties.TOKEN_HAEDER);

        log.info("token => {} ", header);

        if(header == null || !header.startsWith(JwtProperties.TOKEN_PRIFIX)){

            log.info("check");
            chain.doFilter(request,response);
            return;
        }

        String token = header.replace(JwtProperties.TOKEN_PRIFIX, "");

        Long userId = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build().verify(token)
                .getClaim("id").asLong();

        log.info("userId => {}",userId);


        //토큰의 유효기간이 만료되었을 때 refresh cookie를 어떻게 활용할지, 교차검증은 어떻게 할지, 메모리 db를 쓸지, rdbms를 쓸지 한 번 알아서 공부해서 구현해보시는 거 추천
        //rdbms(관계향 DB) 는 느리기 때문에 사용자경험(UX)를 좋게 하기 위해서 jwt를 저장하고 검증하는데는 사실 거의 쓰이지 않음.
        //그리고 과연 refresh token을 어디다 보관하는 것이 가장 좋을지.. 사실 제 소견에는 이건 방법이 없음. 보안 때문에 refresh, access token 두 개를 쓴다해도 결국 그 2개는
        //클라이언트 사이드에 저장되어야 하는데, 해킹하고자 맘 먹는 사람에게는 다 털리게 되어있음. 사실 이런 문제 때문에 세션이 가장 보안에 유리하긴 하지만, 세션은 무겁고 확장하기가 까다로움
        //암튼 제 생각에는 사실 refresh token 같은 건 별 의미가 없다고 생각함!(주관적인 생각임). 암튼 공부해보셔서 구현해보시는 걸 추천. 추천 블로그
        //https://www.bezkoder.com/spring-boot-react-jwt-auth/
        //https://cheese10yun.github.io/spring-oauth2-provider/


        if(userId != null){

            log.info("잘 갖고왔다!!");

            User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유효하지 않은 User"));

            PrincipalDetails principalDetails = new PrincipalDetails(user);

            session.setAttribute("principal", principalDetails); //필요할 수도 있고 안 필요할 수도 있는데

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            principalDetails,
                            principalDetails.getPassword(),
                            principalDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication); //인증 되었다는 거에요.

        }

        chain.doFilter(request, response);

    }
}
