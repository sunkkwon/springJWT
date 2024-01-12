package com.study.spring.springjwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@ResponseBody
public class MainController {

	@GetMapping("/")
	public String mainP() {
		return "Main Controller";
	}
	
}
