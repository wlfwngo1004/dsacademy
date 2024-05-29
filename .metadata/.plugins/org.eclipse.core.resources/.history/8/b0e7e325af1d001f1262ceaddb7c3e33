<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/include/css/loginForm.css">

<script type="text/javascript">
	
	
	function placeholderEvent(inputElement) {
	    inputElement.click(function(){
	        if ($(this).val() == '') {
	            $(this).attr('placeholder', '');
	        }
	    });
	
	    inputElement.blur(function(){
	        if ($(this).val() == '') {
	            $(this).attr('placeholder', $(this).data('placeholder'));
	        }
	    });
	}
	
	$(function(){
		
		let errorMsg = "${errorMsg}";
		if(errorMsg!=""){
			alert(errorMsg);
			errorMsg = "";
		}
		
		placeholderEvent($('#email'));
		placeholderEvent($('#inputPwd'));
		
		$('#loginBtn').click(function(){
			if(!chkData("#email","아이디를")) return;
			else if(!chkData("#inputPwd","비밀번호를")) return;
			else {
				$('#loginForm').attr({
					"method":"post",
					"action":"/member/normal/login"
				});
				$('#loginForm').submit();
			}
		})
		
		
	}) // $종료
</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div id="wrap2">
	    <h2 class="loginCss">로그인</h2>
	    	<div>
	    		<form id="loginForm">
	    			<div>
	    				<label for="user_email" class="labelCss">아이디</label><br>
	    				<input type="text" id="email" name="email" 
	    				placeholder="아이디 또는 이메일을 입력하세요." data-placeholder="아이디 또는 이메일을 입력하세요"/>
	    			</div>
	    			<div>
	    				<label for="user_pwd" class="labelCss">비밀번호</label><br>
	    				<input type="password" id="inputPwd" name="MPwd" 
	    				placeholder="******" data-placeholder="******"/>
	    			</div>
	    			<div id="textRight">
                        <input type="button" value="로그인" id="loginBtn"/>
                    </div>
                    <div id="css1">
                    <a href="/member/normal/join" id="join">회원가입</a>
                    <a href="/member/findPwdForm" id="find_pwd">아이디/비밀번호 찾기</a>
                    </div>
	    		</form>
	    	</div>
	    	<div>
	    		<a href="${naverUrl}" id="naverLogin">
	    			<span class="icon">
	    				<img src="/image/naverIcon.png" alt="naver">
	    			</span>
	    			네이버로 로그인
	    		</a>
	    	</div>
	    	<div>
	    		<a href="/member/loginForm" id="kakaoLogin">
	    			<span class="icon">
	    				<img src="/image/kakaoIcon.png" alt="kakao">
	    			</span>
	    			카카오로 로그인
	    		</a>
	    	</div>
    </div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>

