package com.security.basic.auth;

import com.security.basic.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//로그인 진행이 완료가 되면 시큐리티 session을 만들어줌.
//오브젝트 => Authentication 타입 객체
//Authentication 안에 User 정보가 있어야 됨
//User 오브젝트 타입 => UserDetails 타입 객체

//Security Session => Authentication => UserDetails(PrinciplaDetails)

@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user;

    //해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
