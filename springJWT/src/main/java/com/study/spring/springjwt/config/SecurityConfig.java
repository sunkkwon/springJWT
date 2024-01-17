package com.study.spring.springjwt.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.study.spring.springjwt.jwt.JWTFilter;
import com.study.spring.springjwt.jwt.JWTUtil;
import com.study.spring.springjwt.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// AuthenticationManager가 인자로 받을 AuthenticationConfiguration 객체를 생성자 주입
	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {
		this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
	}
	
	// authenticationManager Bean 등록
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }	
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    	//cors custom setting
    	http.cors((cors) -> cors.configurationSource(corsConfigurationSource()));
    	
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

        //JWTFilter 등록
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		//세션 설정
        // JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정하는 것이 중요하다.
        http.sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
    
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration();
    	
    	configuration.setAllowedOrigins(Arrays.asList("http://api.example.com"));
    	configuration.setAllowedMethods(Arrays.asList("GET","POST"));
    	configuration.addExposedHeader("Authentication");
    	
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", configuration);
    	
    	return source;
    }
}
