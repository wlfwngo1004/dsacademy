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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ds.academy.client.member.service.LoginService;
import com.ds.academy.client.member.vo.AthoVO;
import com.ds.academy.client.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Setter(onMethod_ = @Autowired)
	private LoginService loginService;
	
	private String CLIENT_ID = "JeQw11B1TjRvCmFW1foW";
	private String CLI_SECRET = "yeDEG4UYMk";
	private String userInfo;
	
	
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
	public String callback(HttpServletRequest request, MemberVO mvo, Model model, HttpSession session) throws IOException, ParseException {
		
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		
		userInfo = getUserInfoFromNaver(code, state);
		
		JSONObject userInfoJson = new JSONObject(userInfo);
		JSONObject responseJson = userInfoJson.getJSONObject("response");
		
		String email = responseJson.getString("email");
		System.out.println(email);
		
		if(userInfo != null && !userInfo.isEmpty()) {
			MemberVO checkUser = loginService.checkUser(email);
			model.addAttribute("userInfo", checkUser);
			System.out.println(checkUser.getMAthoNum());
			return "member/check";
		}else {
			model.addAttribute("loginFailedMessage", "사용자 정보를 가져오지 못했습니다.");
			System.out.println("user Info 못가져옴");
			return "member/check";
			
		}
		
	}
	
	
	// 인증번호 확인 & 회원가입 절차
	@PostMapping("/naver/atho")
	public String athoCheck(@ModelAttribute AthoVO avo, MemberVO mvo, RedirectAttributes ras, HttpSession session) {
		log.info("인증번호 체크 호출");
		
		// 인증번호 체크 (유효한 인증번호인지!)
		AthoVO checkAtho = loginService.checkAtho(avo);
		// 이미 저장한 사용자인지 체크
		MemberVO checkMember = loginService.checkMember(avo);
		
		
		// userInfo를 JSON 객체로 파싱
		JSONObject userInfoJson = new JSONObject(userInfo);
		JSONObject responseJson = userInfoJson.getJSONObject("response");
		
		// 사용자 정보 추출
		String email = responseJson.getString("email");
		String name = responseJson.getString("name");
		String gender = responseJson.getString("gender");
		String mobile = responseJson.getString("mobile");
		String password = generateRandomPassword();
		
		// 이름 유니코드에서 일반 문자열로
		name = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFC);
		
		String url = "";
		
		// 인증번호가 유효하고 같은 인증번호가 없는 경우 회원가입
		if(checkAtho != null && checkMember == null) {
			mvo.setMAthoNum(avo.getMAthoNum());
			mvo.setEmail(email);
			mvo.setName(name);
			mvo.setGender(gender);
			mvo.setMobile(mobile);
			mvo.setMPwd(password);
			
			int joinMember = loginService.joinMember(mvo);
			
			if(joinMember > 0) {
				session.setAttribute("loginMember", mvo);
				url = "/";
			} else {
				ras.addFlashAttribute("errorMsg", "회원 가입에 실패하였습니다.");
				url = "/member/loginForm";
			}
			
			 
		} else if(checkAtho == null){
			ras.addFlashAttribute("errorMsg", "인증번호가 존재하지 않거나 유효하지 않습니다.");
			System.out.println(2);
			url = "/member/loginForm";
		} else {
			// 이미 가입한 사용자들 처리.
			mvo.setMAthoNum(avo.getMAthoNum());
			mvo.setEmail(email);
			mvo.setName(name);
			mvo.setGender(gender);
			mvo.setMobile(mobile);
			mvo.setMPwd(password);
			session.setAttribute("loginMember", mvo);
			
			url = "/";
		}
		
		
		return "redirect:"+url;
	}

	
	
	// 세션 무효화(로그아웃)
	@RequestMapping("/naver/invalidate")
	public String invalidateSession(HttpSession session) {
	    session.invalidate();
	    return "redirect:/member/loginForm";
	  }
	
	@RequestMapping("/normal/join")
	public String normalJoinPage() {
		return "member/joinForm";
	}
	
	
	// 세션 없이 사용자 정보 추출 & 실제 json 데이터 추출 메소드.
	private String getUserInfoFromNaver(String code, String state) throws IOException {
		String redirectURI = URLEncoder.encode("http://localhost:8080/member/naver/callback", "UTF-8");
		
		// 네이버 API로 사용자 정보를 가져오기 위한 URL 생성 accessToken 얻기
		String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&"
				+ "client_id=" + CLIENT_ID
				+ "&client_secret=" + CLI_SECRET
				+ "&redirect_uri=" + redirectURI
				+ "&code=" + code
				+ "&state=" + state;
		
		String response = requestToServer(apiURL);
		
		// 응답에서 accessToken 추출하여 사용자 정보를 가져오는 URL 생성
		if(response != null && !response.isEmpty()){
			JSONObject jsonResponse = new JSONObject(response);
			String accessToken = jsonResponse.getString("access_token");
			String userInfoURL = "https://openapi.naver.com/v1/nid/me";
		    String headerStr = "Bearer " + accessToken; // Bearer 다음에 공백 추가
		    String userInfoResponse = requestToServer(userInfoURL, headerStr);
			return userInfoResponse;
			
		} else {
			return null;
		}		
	}
	
	private String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(10);
		
		for(int i = 0; i < 10; i++) {
			int randomIndex = random.nextInt(characters.length());
			sb.append(characters.charAt(randomIndex));
		}
		
		return sb.toString();
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
