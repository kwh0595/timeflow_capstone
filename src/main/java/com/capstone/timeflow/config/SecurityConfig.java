package com.capstone.timeflow.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig{
    @Bean //password 암호화를 위한 BCryptPasswordEncoder 클래스 생성 및 등록
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //resouces를 접근할 수 있도록 한 빈
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 로컬에서 확인하기 위해 csrf 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()// 해당 경로에서는 로그인 없이 접근 가능
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form // 성공하면 메인 페이지로 이동
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .usernameParameter("userMail")
                        .passwordParameter("userPassword")
                        .permitAll()
                        .defaultSuccessUrl("/main", true)
                )
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
