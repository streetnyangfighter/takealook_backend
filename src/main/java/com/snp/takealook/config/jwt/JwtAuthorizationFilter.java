//package com.snp.takealook.config.jwt;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.snp.takealook.api.domain.user.User;
//import com.snp.takealook.api.repository.user.UserRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Slf4j
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private final UserRepository userRepository;
//    private final HttpSession session;
//
//    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, HttpSession session) {
//        super(authenticationManager);
//        this.userRepository = userRepository;
//        this.session = session;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        log.info("권헌이 필요한 요청이 들어옴");
//
//        String header = request.getHeader(JwtProperties.TOKEN_HAEDER);
//
//        log.info("token=>{}", header);
//
//        if(header == null || !header.startsWith(JwtProperties.TOKEN_PRIFIX)){
//
//            log.info("check");
//            chain.doFilter(request,response);
//            return;
//        }
//
//        String token = header.replace(JwtProperties.TOKEN_PRIFIX, "");
//
//        Long userId = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build().verify(token)
//                .getClaim("id").asLong();
//
//        log.info("userId=>{}",userId);
//
//        if(userId != null){
//
//            log.info("잘 갖고왔다!!");
//
//            User user = userRepository.findById(userId).orElseThrow(()->{
//                return new UserNotFoundException("유저 정보를 찾을 수 없습니다!!");
//            });
//
//            PrincipalDetails principalDetails = new PrincipalDetails(user);
//
//            session.setAttribute("principal", principalDetails); //필요할 수도 있고 안 필요할 수도 있는데
//
//            Authentication authentication =
//                    new UsernamePasswordAuthenticationToken(
//                            principalDetails,
//                            principalDetails.getPassword(),
//                            principalDetails.getAuthorities()
//                    );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication); //인증 되었다는 거에요.
//
//
//        }
//
//        chain.doFilter(request, response);
//
//    }
//}
