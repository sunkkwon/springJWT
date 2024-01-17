package com.study.spring.springjwt.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.study.spring.springjwt.dto.CustomUserDetails;
import com.study.spring.springjwt.entity.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;

	public JWTFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// request에서 authorization header를 찾음
		String authorization = request.getHeader("Authorization");
//		System.out.println(authorization);

		// Authorization header 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			System.out.println("toke  null");
			filterChain.doFilter(request, response);

			// 조건에 해당하면 메소등 종료
			return;
		}

		System.out.println("authorization now");
		// Bearer 부분 제거후 순수 token만 획득
		String token = authorization.split(" ")[1];

		// 토큰 소별시간 검증
		if (jwtUtil.isExpired(token)) {
			System.out.println("token expired");
			filterChain.doFilter(request, response);

			// 조건에 해당하면 메소드 종료 필수
			return;
		}

		// 토큰에서 username, role 획득
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);
		
		// userentity 를 생성하여 값 set
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(username);
		userEntity.setPassword("temppassword");
		userEntity.setRole(role);
		
		//UserDetails에 회원 정보 객체 담기
		CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
		
		//스프링 시큐리티 인증 토큰 생성
		Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
		
		//세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		filterChain.doFilter(request, response);
			
		
	}

}
