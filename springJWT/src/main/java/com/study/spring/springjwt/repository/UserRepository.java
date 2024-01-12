package com.study.spring.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.spring.springjwt.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	// user가 잇는지 확인
	Boolean existsByUsername(String username);

}
