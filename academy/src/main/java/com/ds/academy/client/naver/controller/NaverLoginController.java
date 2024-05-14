package com.ds.academy.client.naver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ds.academy.client.member.service.NaverService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/naver/*")
public class NaverLoginController {
	
	private final NaverService naeverService;
	
	@GetMapping("/callback")
	public String callback() {
		return "member/check";
	}
	
}
