package com.ds.academy.client.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/video/*")
public class VideoController {
	
	@GetMapping("/videoForm")
	public String videoForm() {
		return "video/course";
	}
}
