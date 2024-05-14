package com.ds.academy.client.member.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ds.academy.client.member.service.NaverService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	private final NaverService naverService;
	
	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		log.info("로그인폼 호출");
		
		model.addAttribute("naverUrl", naverService.getNaverLogin());
		return "member/loginForm";
	}

}
