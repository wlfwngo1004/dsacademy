package com.ds.academy.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class main {
	@RequestMapping("/")
	public String mainPage() {
		return "main";
	}
}
