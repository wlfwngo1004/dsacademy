package com.ds.academy.client.member.controller;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	//private final NaverService naverService;
	
	private String CLIENT_ID = "JeQw11B1TjRvCmFW1foW";
	private String CLI_SECRET = "yeDEG4UYMk";
	
	
	@GetMapping("/loginForm")
	public String loginForm(HttpSession session, Model model) throws UnsupportedEncodingException, UnknownHostException  {
		log.info("로그인폼 호출");
		
		String redirectURI = URLEncoder.encode("http://localhost:8080/member/naver/callback", "UTF-8");
		
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", CLIENT_ID, redirectURI, state);
		session.setAttribute("state", state);
		
		model.addAttribute("naverUrl", apiURL);
		
		//model.addAttribute("naverUrl", naverService.getNaverLogin());
		return "member/loginForm";
	}

	// 네이버 콜백 페이지
	@GetMapping("/naver/callback")
	public String callback(HttpSession session, HttpServletRequest request, Model model) throws IOException, ParseException {
		
		 String code = request.getParameter("code"); 
		 String state = request.getParameter("state"); 
		 String redirectURI = URLEncoder.encode("http://localhost:8080/member/naver/callback", "UTF-8");
		 
		 String apiURL;
		 apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		 apiURL += "client_id=" + CLIENT_ID;
		 apiURL += "&client_secret=" + CLI_SECRET;
		 apiURL += "&redirect_uri=" + redirectURI;
		 apiURL += "&code=" + code;
		 apiURL += "&state=" + state;
		 System.out.println("apiURL=" + apiURL);
		 
		 String res = requestToServer(apiURL);
		 
		 if(res != null && !res.equals("")) {
			 model.addAttribute("res", res);
			 Map<String, Object> parsedJson = new JSONParser(res).parseObject();
			 System.out.println(parsedJson);
			 
			 session.setAttribute("currentUser", res);
			 session.setAttribute("currentAT", parsedJson.get("access_token"));
			 session.setAttribute("currentRT", parsedJson.get("refresh_token"));
		 } else {
			 model.addAttribute("res", "Login failed!");
		 }
		
		return "member/check";
		
	}
	
	@ResponseBody
	@RequestMapping("/naver/getProfile")
	public String getProfileFromNaver(String accessToken) throws IOException {
	    // 네이버 로그인 접근 토큰;
	    String apiURL = "https://openapi.naver.com/v1/nid/me";
	    String headerStr = "Bearer " + accessToken; // Bearer 다음에 공백 추가
	    String res = requestToServer(apiURL, headerStr);
	    return res;
	  }
	
	
	// 세션 무효화(로그아웃)
	@RequestMapping("/naver/invalidate")
	  public String invalidateSession(HttpSession session) {
	    session.invalidate();
	    return "redirect:/member/loginForm";
	  }
	
	
	/**
	   * 서버 통신 메소드
	   * @param apiURL
	   * @return
	   * @throws IOException
	   */
	  private String requestToServer(String apiURL) throws IOException {
	    return requestToServer(apiURL, "");
	  }
	  /**
	   * 서버 통신 메소드
	   * @param apiURL
	   * @param headerStr
	   * @return
	   * @throws IOException
	   */
	  private String requestToServer(String apiURL, String headerStr) throws IOException {
	    URL url = new URL(apiURL);
	    HttpURLConnection con = (HttpURLConnection)url.openConnection();
	    con.setRequestMethod("GET");
	    System.out.println("header Str: " + headerStr);
	    if(headerStr != null && !headerStr.equals("") ) {
	      con.setRequestProperty("Authorization", headerStr);
	    }
	    int responseCode = con.getResponseCode();
	    BufferedReader br;
	    System.out.println("responseCode="+responseCode);
	    if(responseCode == 200) { // 정상 호출
	      br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    } else {  // 에러 발생
	      br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	    }
	    String inputLine;
	    StringBuffer res = new StringBuffer();
	    while ((inputLine = br.readLine()) != null) {
	      res.append(inputLine);
	    }
	    br.close();
	    if(responseCode==200) {
	      return res.toString();
	    } else {
	      return null;
	    }
	  }
}
