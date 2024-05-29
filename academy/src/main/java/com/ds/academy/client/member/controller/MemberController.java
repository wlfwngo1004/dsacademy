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
import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
	private String KAKAO_RESTAPI_KEY = "3b1bd2aee5ee3965c49f743096da2d8c";
	private String KAKAO_CLI_SECRET = "uXnrfzdYXuHyzBUHTteRHFvSI19VtEPM";
	private String KAKAO_REDIRECTURI = "http://localhost:8080/member/kakao/callback";
	private String userInfo;
	
	
	@GetMapping("/loginForm")
	public String loginForm(HttpSession session, Model model) throws UnsupportedEncodingException, UnknownHostException  {
		log.info("로그인폼 호출");
		
		// 네이버 로그인 인가코드 받기
		String redirectURI = URLEncoder.encode("http://localhost:8080/member/naver/callback", "UTF-8");
		
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += String.format("&client_id=%s&redirect_uri=%s&state=%s", CLIENT_ID, redirectURI, state);
		//session.setAttribute("state", state);
		
		model.addAttribute("naverUrl", apiURL);
		
		
		// 카카오 로그인 인가코드 받기
		String kakaoRidirectURI = URLEncoder.encode("http://localhost:8080/member/kakao/callback", "UTF-8");
		String kakaoURL = "https://kauth.kakao.com/oauth/authorize?response_type=code";
		kakaoURL += String.format("&client_id=%s&redirect_uri=%s", KAKAO_RESTAPI_KEY, kakaoRidirectURI);
		model.addAttribute("kakaoUrl", kakaoURL);
		log.info(kakaoURL);
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
			if(checkUser != null) {
				model.addAttribute("userInfo", checkUser);
				System.out.println(checkUser.getMAthoNum());
				return "member/check";
			} else {
				return "member/check";
			}
		}else {
			model.addAttribute("loginFailedMessage", "사용자 정보를 가져오지 못했습니다.");
			System.out.println("user Info 못가져옴");
			return "member/check";
			
		}
		
	}
	
	/* 네이버 콜백 페이지
	@GetMapping("/kakao/callback")
	public String kakaoCallback(HttpServletRequest request, MemberVO mvo, Model model, HttpSession session) throws IOException, ParseException {
		String code = request.getParameter("code");
	
	
	}*/
	
	
	
	// 인증번호 확인 & 회원가입 절차
	@PostMapping("/naver/atho")
	public String athoCheck(@ModelAttribute AthoVO avo, @RequestParam(value = "grade", required = false) String grade, @RequestParam(value = "schoolName", required = false) String schoolName, MemberVO mvo, RedirectAttributes ras, HttpSession session) {
		log.info(avo.getMAthoNum() + grade + schoolName);
		
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
			mvo.setGrade(grade);
			mvo.setSchoolName(schoolName);
			
			log.info(mvo.toString());
			
			int joinMember = loginService.joinMember(mvo);
			
			if(joinMember > 0) {
				session.setAttribute("loginMember", mvo);
				url = "/";
			} else {
				ras.addFlashAttribute("errorMsg", "회원가입에 실패하였습니다. 관리자에게 문의 바랍니다.");
				url = "/member/loginForm";
			}
			
			 
		} else if(checkAtho == null){
			ras.addFlashAttribute("errorMsg", "인증번호가 존재하지 않거나 유효하지 않습니다.");
			url = "/member/loginForm";
		} else {
			// 이미 가입한 사용자들 처리.
			log.info(checkMember.toString());
			session.setAttribute("loginMember", checkMember);
			
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
	
	
	// --------------------------일반 회원 가입--------------------------------------------------------------------------------------
	
	@PostMapping("/normal/login")
	public String normalLogin(@ModelAttribute MemberVO mvo, HttpSession session, RedirectAttributes ras) {
		log.info(mvo.toString());
		MemberVO login = loginService.loginMember(mvo);
		String url = "";
		
		if(login != null) {
			log.info(login.toString());
			session.setAttribute("loginMember", login);
			url = "/";
		} else {
			ras.addFlashAttribute("errorMsg", "아이디 또는 비밀번호가 옳바르지 않습니다. 확인해주세요.");
			url = "/member/loginForm";
		}
		return "redirect:"+url;
	}
	
	
	// 회원가입 창
	@RequestMapping("/normal/join")
	public String normalJoinPage() {
		return "member/joinForm";
	}
	
	// 아이디 중복체크 ajax 반환 ResponseBody
	@PostMapping("/normal/idCheck")
	@ResponseBody
	public String idCheck(@ModelAttribute MemberVO mvo) {
		log.info("중복체크 호출");
		String result = "";
		MemberVO idCheck = loginService.checkId(mvo);
		if(idCheck != null) {
			result = "중복";
		} else {
			result = "성공";
		}
		return result;
	}
	
	// 일반 회원가입
	@PostMapping("/normal/insertProcess")
	public String joinMember(@ModelAttribute MemberVO mvo, AthoVO avo, RedirectAttributes ras) {
		log.info(mvo.toString());
		String url = "";
		avo.setMAthoNum(mvo.getMAthoNum());
		AthoVO athoCheck = loginService.checkAtho(avo);
		MemberVO memberCheck = loginService.checkMember(avo);
		
		
		if(athoCheck != null && memberCheck == null) {
			// 인증번호가 생성되어 있고 해당 인증번호로 회원으로 등록되지 않은경우에는 회원등록.
			log.info("존재하는 인증번호 회원으로 등록되지 않은 회원");
			int joinMember = loginService.joinNormalMember(mvo);
			if(joinMember > 0) {
				ras.addFlashAttribute("errorMsg", "회원가입이 완료되었습니다. 로그인 후 사이트를 이용해주세요.");
				url = "/";
			}else {
				ras.addFlashAttribute("errorMsg", "회원가입에 실패하였습니다. 관리자에게 문의 바랍니다.");
				url = "/member/normal/join";
			}
		}else if(athoCheck != null && memberCheck != null) {
			// 인증번호는 생성된 인증번호이지만 이미 회원으로 등록된 인증번호 -> 이미 사용된 인증번호입니다.
			ras.addFlashAttribute("errorMsg", "이미 사용된 인증번호 입니다. 관리자에게 옳바른 인증번호를 발급받으세요.");
			url = "/member/normal/join";
		}else {
			// 인증번호 자체가 없는 상태
			ras.addFlashAttribute("errorMsg", "인증번호가 존재하지 않거나 유효하지 않습니다.");
			url = "/member/normal/join";
		}
		
		
		return "redirect:"+url;
	}
	
	// 아이디&비밀번호 찾기 화면
	@RequestMapping("/findPwdForm")
	public String findPassword() {
		return "/member/findPasswordForm";
	}
	
	// 아이디 찾기 ajax 데이터
	@PostMapping("/findId")
	@ResponseBody
	public Map<String, Object> findId(@ModelAttribute MemberVO mvo, Model model) {
		log.info("findId 호출 성공");
		log.info(mvo.getName() + mvo.getMAthoNum());
		
		Map<String, Object> resultMap = new HashMap<>();
		MemberVO findId = loginService.findId(mvo);
		
		if (findId != null) {
			log.info(findId.toString());
			
			// 아이디 마스크 처리
			String email = findId.getEmail();
			String maskedUserEmail;
			if(email.length() > 4) {
				String visiblePart = email.substring(0, 4);
				String maskedPart = "*".repeat(email.length()-4);
				maskedUserEmail = visiblePart + maskedPart;
			} else {
				maskedUserEmail = email;
			}
			
			resultMap.put("status", "성공");
			resultMap.put("userId", maskedUserEmail);
		} else {
			resultMap.put("status", "실패");
			resultMap.put("message", "해당하는 아이디가 존재하지 않습니다.<br>사용자 정보를 확인하세요.");
		}
		
		return resultMap;
	}
	
	// 비밀번호 찾기 ajax 데이터
	@PostMapping("/findPassword")
	@ResponseBody
	public Map<String, Object> findPassword(@ModelAttribute MemberVO mvo){
		log.info("findPassword 호출 성공");
		log.info(mvo.getName() + mvo.getEmail() + mvo.getMAthoNum());
		
		Map<String, Object> resultMap = new HashMap<>();
		MemberVO findPassword = loginService.findPassword(mvo);
		
		
		if(findPassword != null) {
			String password = findPassword.getMPwd();
			String maskedPassword;
			if(password.length() > 4) {
				String visiblePart = password.substring(0, 4);
				String maskedPart = "*".repeat(password.length() - 4);
				maskedPassword = visiblePart + maskedPart;
			} else {
				maskedPassword = password;
			}
			
			resultMap.put("status", "성공");
			resultMap.put("userPassword", maskedPassword);
		} else {
			resultMap.put("status", "실패");
			resultMap.put("message", "해당하는 아이디 또는 비밀번호가 존재하지 않습니다. <br>사용자 정보를 확인하세요.");
		}
		
		return resultMap;
	}
	
	@RequestMapping("/updatePwdForm")
	public String updatePwdForm() {
		log.info("updatePwdForm 호출");
		return "member/updatePwdForm";
	}
	
	// 새로운 비밀번호로 변경
	@PostMapping("/updatePwd")
	public String updatePwd(@ModelAttribute MemberVO mvo, RedirectAttributes ras) {
		
		
		
		int updatePwdSuccess = loginService.updatePwd(mvo);
		
		if(updatePwdSuccess > 0) {
			log.info("비밀번호 변경 성공");
			ras.addFlashAttribute("updatePwdSuccess", true);
			return "redirect:/member/updatePwdForm";
		} else {
			ras.addFlashAttribute("errorMsg", "비밀번호 변경에 실패하였습니다. 인증번호를 확인하세요.");
			return "redirect:/member/updatePwdForm";
		}
		
	}
	
	// ---------------------------------------------- 카카오 로그인 구현 -------------------------------------------------------
	
	
	
	
	
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
	
	/* --카카오 인가코드를 이용해서 accessToken을 가져오기
	private String getAccessToken(String code) {
		String tokenUrl = "https://kauth.kakao.com/oauth/token";
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		Map<String, String> params = new HashMap<>();
		params.put("grant_type", "authorization_code");
		params.put("client_id", KAKAO_RESTAPI_KEY);
		params.put("redirect_uri", KAKAO_REDIRECTURI);
		params.put("code", code);
		params.put("client_secret", KAKAO_CLI_SECRET);
	}*/
	
	
	
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
