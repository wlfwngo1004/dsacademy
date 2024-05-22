<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
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
		
	}) // $종료
</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div id="wrap2">
	    <h2 class="loginCss">로그인</h2>
	    	<div>
	    		<form id="loginForm">
	    			<div>
	    				<label for="user_email" class="labelCss">이메일</label><br>
	    				<input type="text" id="email" name="user_email" 
	    				placeholder="example@naver.com" data-placeholder="example@naver.com"/>
	    			</div>
	    			<div>
	    				<label for="user_pwd" class="labelCss">비밀번호</label><br>
	    				<input type="text" id="inputPwd" name="user_pwd" 
	    				placeholder="******" data-placeholder="******"/>
	    			</div>
	    			<div id="textRight">
                        <button class="btn btn-primary" type="button" id="loginBtn">로그인</button>
                    </div>
                    <div id="css1">
                    <a href="/member/normal/join" id="join">회원가입</a>
                    <a href="/member/loginForm" id="find_pwd">비밀번호 찾기</a>
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

