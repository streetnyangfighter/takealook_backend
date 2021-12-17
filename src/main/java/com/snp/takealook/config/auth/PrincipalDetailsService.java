package com.snp.takealook.config.auth;

import com.snp.takealook.api.domain.user.User;
import com.snp.takealook.api.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override // AuthenticationManager 가 보내줌
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username=>{}",username);

        //AuthenticationFilter 가 일로 보내줌
        User principal = userRepository.findByUsername(username);

        if (principal == null) {
            return null;
        } else {

            return new PrincipalDetails(principal);
        }
    }
}

