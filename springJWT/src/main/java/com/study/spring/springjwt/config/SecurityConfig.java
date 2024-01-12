package com.study.spring.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }	
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		//csrf disable
        http.csrf((auth) -> auth.disable());

		//From 로그인 방식 disable
        http.formLogin((auth) -> auth.disable());

		//http basic 인증 방식 disable
        http.httpBasic((auth) -> auth.disable());

		//경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
            .requestMatchers("/login", "/", "/join").permitAll() 	// 3개의 경우는 허용
			.requestMatchers("/admin").hasRole("ADMIN")		// admin role 이 있는 겨웅만 대상
            .anyRequest().authenticated());		// 이외의 경우는 로그인 인증필요

		//세션 설정
        // JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정하는 것이 중요하다.
        http.sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
