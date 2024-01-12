package com.study.spring.springjwt.dto.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.spring.springjwt.dto.joinDTO;
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
	
	public void joinS(joinDTO dto) {
		String username = dto.getUsername();
		String password = dto.getPassword();
//		String role = dto.getRole();
	
		Boolean isExists = userRepository.existsByUsername(username);
		
		if(isExists) {
			return ;
		}
		UserEntity data = new UserEntity();
		
		data.setName(username);
		data.setPassword(bCryptPasswordEncoder.encode(password));
		data.setRole("ROLE_ADMIN");
		
		userRepository.save(data);
	}
}
