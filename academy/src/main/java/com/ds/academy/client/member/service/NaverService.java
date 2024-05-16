package com.ds.academy.client.member.service;



import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;






@Service
public class NaverService {
	
	@Value("${naver.client.id}")
	private String NAVER_CLIENT_ID;
	
	@Value("${naver.client.secret}")
	private String NAVER_CLIENT_SECRET;
	
	@Value("${naver.redirect.url}")
	private String NAVER_REDIRECT_URL;
	
	private final static String NAVER_AUTH_URI = "https://nid.naver.com";
	private final static String NAVER_API_URI = "http://openapi.naver.com";
	
	public String getNaverLogin() {
		return NAVER_AUTH_URI + "/oauth2.0/authorize"
				+ "?response_type=code"
				+ "&client_id=" + NAVER_CLIENT_ID
				+ "&state=STATE_STRING"
				+ "&redirect_uri=" + NAVER_REDIRECT_URL;
				
	}
	
	
	
	 public String getAccessToken(String code, String state, String redirectURI) {
		 
		 String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		 apiURL += "client_id" + NAVER_CLIENT_ID;
		 apiURL += "&client_secret=" + NAVER_CLIENT_SECRET;
		 apiURL += "&redirect_uri=" + redirectURI;
		 apiURL += "&code=" + code;
		 apiURL += "&state=" + state;
		 return apiURL;
	 }
	 
	 
	
	
}
