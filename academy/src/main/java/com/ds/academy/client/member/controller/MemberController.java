package com.ds.academy.client.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ds.academy.client.member.dao.MemberDao;
import com.ds.academy.client.member.vo.Member;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/member/*")
@Slf4j
@Controller
public class MemberController {
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping("/loginForm")
	public String loginForm(@ModelAttribute Member mvo, Model model) {
		List<Member> members = memberDao.memberList(mvo);
		model.addAttribute("members", members);
		
		log.info("로그인폼 호출");
		return "member/loginForm";
	}

}
