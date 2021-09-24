package com.security.jwt.config;

import com.security.jwt.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http  //HttpSecurity 객체 설정
                .csrf().disable()
                //세션 사용하지 않겠다는 의미
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .addFilter(corsFilter) //@CrossOrigiin(인증X), 시큐리티 필터에 등록 인증O
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                //권한요청 처리 설정 메서드임
                .authorizeRequests()
                .antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                //"/api/v1/admin/**" 주소는 ROLE_ADMIN 권한만 접근 가능
                .antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
                //다른 요청은 누구든지 접근할 수 있음
                .anyRequest().permitAll();
    }
}
