package com.study.spring.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.spring.springjwt.entity.UserEntity;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	// user가 잇는지 확인
	boolean existsByUsername(String username);
	
	UserEntity findByUsername(String username);

}
