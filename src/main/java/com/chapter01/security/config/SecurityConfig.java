package com.chapter01.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        // permitAll : 모든사용자에게 로그인없이 설정할 수있는 메소드
                        // hasRole : 특정한 권한이 있어야 접근 가능
                        // authenticated : 로그인하면 모두 접근 가능
                        // denyAll : 로그인을 해도 모든 사용자의 접근 제한
                        .requestMatchers("/", "/login", "/join", "/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                        );
        // form 로그인 방식
        http
                .formLogin((auth) -> auth
                        // 로그인 페이지 path
                        .loginPage("/login")
                        // 로그인 프로세스 path
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );
        // httpBasic 방식
//        http
//                .httpBasic(Customizer.withDefaults());
//        http
//                .csrf((auth) -> auth
//                        .disable()
//                );

        http
                .sessionManagement((auth) -> auth
                        // 최대 허용할 수 있는 동시 접속 로그인 수
                        .maximumSessions(1)
                        /**
                         * 최대 허용수가 넘으면 기존 로그인을 끊을지 말지 설정
                         * true : 초과시 새로운 로그인 차단
                         * false : 초과시 기존 세선 하나 삭제
                         */
                        .maxSessionsPreventsLogin(true)
                );

        http
                .sessionManagement((auth) -> auth
                        /**
                         * none : 로그인 시 세션 정보 변경 안함
                         * newSession : 로그인 시 세션 새로 생성
                         * changeSessionId : 로그인 시 동일한 세션에 대한 id 변경
                         */
                        .sessionFixation().changeSessionId()
                );

        return http.build();
    }

    // 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // in memory 방식 -> 거의 안씀
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails superAdmin = User.builder()
//                .username("superAdmin")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(superAdmin);
//    }
}