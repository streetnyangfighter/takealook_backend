package com.snp.takealook.config.auth;

import com.snp.takealook.api.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override// 계정이 만료되지 않았는 지 리턴 (true: 만료안됨)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override// 계정이 잠겨있지 않았는 지 리턴 (true: 잠기지 않음)
    public boolean isAccountNonLocked() { //예를 들어 아이디 3번 시도했는데 실패했으면 락인한다든가.. 그런 로직
        return true;
    }

    @Override// 비밀번호가 만료되지 않았는 지 리턴 (true: 만료안됨)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {// 계정이 활성화(사용가능)인 지 리턴한다. (true: 활성화)
        return true;
    }

    @Override// 계정이 갖고있는 권한 목록 리턴
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Role 검증 하는 중");
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> "ROLE_USER");

        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
