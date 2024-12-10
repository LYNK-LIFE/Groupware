package com.semi.lynk.config;

import com.semi.lynk.common.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    /*
     *   비밀번호를 인코딩하기 위한 Bean 생성.
     *   Bcrypt 객체는 비밀번호 암호화를 위해 가장 많이 사용되는 알고리즘 중 하나이다.
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 정적 리소스에 대한 요청은 시큐리티 설정에 들지 못하게 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 여기가 핵심!!!!!!!!!!!!!!!!!!!!!!!!
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {



        // 위에서설정한 내용대로 시큐리티 기능 빌드(생성)
        return http.build();
    }

}
