package com.study.spring.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.spring.springjwt.dto.JoinDTO;
import com.study.spring.springjwt.service.JoinService;

@Controller
@ResponseBody
public class JoinController {
	private final JoinService joinService;
	
	public JoinController(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@PostMapping("/join")
	public String joinP(JoinDTO joinDTO) {
		
//		System.out.println("controller joinDTO: "+joinDTO.getUsername());
		joinService.joinS(joinDTO);
		return "ok";
	}
}
