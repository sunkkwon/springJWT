package com.study.spring.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.spring.springjwt.dto.joinDTO;
import com.study.spring.springjwt.dto.service.JoinService;

@Controller
@ResponseBody
public class JoinController {
	private final JoinService joinService;
	
	public JoinController(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@PostMapping("/join")
	public String joinP(joinDTO dto) {
		
		joinService.joinS(dto);
		return "ok";
	}
}
