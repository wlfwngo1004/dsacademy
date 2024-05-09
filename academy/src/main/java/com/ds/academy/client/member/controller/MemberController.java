package com.ds.academy.client.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/member/*")
@Slf4j
@Controller
public class MemberController {
	
	@GetMapping("/loginForm")
	public String loginForm() {
		log.info("로그인폼 호출");
		return "member/loginForm";
	}

}
