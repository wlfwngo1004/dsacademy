package com.ds.academy.client.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



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
	
	/*
	 * public String getAccessToken(String code, String state) { String reqUrl =
	 * "http://nid.naver.com/oauth2.0/token"; RestTemplate restTemplate = new
	 * RestTemplate();
	 * 
	 * // HttpHeader Object HttpHeaders headers = new HttpHeaders();
	 * 
	 * // HttpBody Object MultiValueMap<String, String> params = new
	 * LinkedMultiValueMap<>(); params.addIfAbsent("grant_type",
	 * "authorization_code"); params.add("client_id", NAVER_CLIENT_ID);
	 * params.add("client_secret", NAVER_CLIENT_SECRET); params.add("code", code);
	 * params.add("state", state);
	 * 
	 * // http body params 와 http headers를 가진 엔티티 HttpEntity<MultiValueMap<String,
	 * String>> naverTokenRequest = new HttpEntity<>(params, headers);
	 * 
	 * // reqUrl로 Http 요청, POST 방식 ResponseEntity<String> response =
	 * restTemplate.exchange(reqUrl, HttpMethod.POST, naverTokenRequest,
	 * String.class);
	 * 
	 * String responseBody = response.getBody();
	 * 
	 * 
	 * }
	 */
	
	
}
