package com.study.spring.springjwt.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.spring.springjwt.dto.JoinDTO;
import com.study.spring.springjwt.entity.UserEntity;
import com.study.spring.springjwt.repository.UserRepository;

@Service
//@RequiredArgsConstructor
public class JoinService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void joinS(JoinDTO joinDTO) {
		String username = joinDTO.getUsername();
		String password = joinDTO.getPassword();
//		String role = dto.getRole();
	
		Boolean isExists = userRepository.existsByUsername(username);
//		System.out.println("username: " + username + ", password: " + password);
//		System.out.println(isExists);
		if(isExists) {
			System.out.println("user:"+username+" is exists");
			return ;
		}
		
//		System.out.println("here");
		UserEntity data = new UserEntity();
		
		data.setUsername(username);
//		data.setPassword(password);
		data.setPassword(bCryptPasswordEncoder.encode(password));
		data.setRole("ROLE_ADMIN");
		
		userRepository.save(data);
	}
}
