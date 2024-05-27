<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/include/css/loginForm.css">

<script type="text/javascript">
	$(function(){
		placeholderEvent($('#email'));
		placeholderEvent($('#inputPwd'));
		placeholderEvent($('#inputMathoNum'));
		
		let errorMsg = "${errorMsg}";
		if(errorMsg!=""){
			alert(errorMsg);
			errorMsg = "";
		}
		
		let updatePwdSuccess = "${updatePwdSuccess}";
		if(updatePwdSuccess == "true"){
			const userConfirmed = confirm("비밀번호가 변경되었습니다. 로그인 하시겠습니까?");
			if(userConfirmed){
				 window.location.href = "/member/loginForm";
			}
		}
		
		passwordCheck = false;
		
		
		$('#inputPwd').on('input', function(){
			let newPassword = $('#inputPwd').val();
			const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/
			const passwordInput = $('#inputPwd');
			
			if(newPassword){
				if(passwordRegex.test(newPassword)){
					$('#inputPwd').removeClass("error-bg").addClass("success-bg");
					$('#newPassword_confirm').text("사용 가능한 비밀번호입니다.").css("color", "green");
				} else {
					$('#inputPwd').removeClass("success-bg").addClass("error-bg");
					$('#newPassword_confirm').text("사용 불가능한 비밀번호입니다.").css("color", "red");
				}
			} else {
				passwordInput.removeClass('error-bg success-bg');
				$('#newPassword_confirm').text("");
			}
		});
		
		$('#mPwd_confirm').on('input', function() {
			let mPwd_confirm = $('#mPwd_confirm').val();
			let newPassword = $('#inputPwd').val();
			let newPasswordConfirm = $('#mPwd_confirm');
			
			if(mPwd_confirm){
				if(newPassword === mPwd_confirm) {
					$('#mPwd_confirm').removeClass("error-bg").addClass("success-bg");
					$("#confirm_pwd").text("비밀번호가 일치합니다.").css("color", "green");
					passwordCheck = true;
				} else {
					$('#mPwd_confirm').removeClass("success-bg").addClass("error-bg");
					$("#confirm_pwd").text("비밀번호가 일치하지 않습니다.").css("color", "red");
					passwordCheck = false;
				}
			}else{
				newPasswordConfirm.removeClass('error-bg success-bg');
				$('#confirm_pwd').text("");
				passwordCheck = false;
			}
			
	    });
		
		
		$('#updatePwdBtn').click(function(){
			if(passwordCheck == false){
				alert("새로운 비밀번호와 비밀번호 확인을 똑같이 입력하세요.");
				return;
			} else {
				if(!chkData('#inputPwd', "새로운 비밀번호를")) return;
				else if(!chkData('#mPwd_confirm', "비밀번호 확인을")) return;
				else if(!chkData('#inputMathoNum', "인증번호를")) return;
				else{
					$('#updateForm').attr({
						"method":"post",
						"action":"/member/updatePwd"
					});
					$('#updateForm').submit();
				}
			}
		});
		
		function checkPasswordMatch() {
            let password = $("#inputPwd").val();
            let confirmPassword = $("#mPwd_confirm").val();

            if (password === confirmPassword) {
                $("#inputPwd, #mPwd_confirm").removeClass("no-match").addClass("match");
                $("#confirm_pwd").text("비밀번호가 일치합니다.").css("color", "green");
            } else {
                $("#inputPwd, #mPwd_confirm").removeClass("match").addClass("no-match");
                $("#confirm_pwd").text("비밀번호가 일치하지 않습니다.").css("color", "red");
            }
        }
		
		
	})// $ 끝
	
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
</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div id="wrap2">
		<h2 class="loginCss">비밀번호 변경</h2>
			<form id="updateForm">
			    <div>
			    	<label for="user_email" class="labelCss">새로운 비밀번호</label><br>
			    	<input type="password" id="inputPwd" name="MPwd"
			    	placeholder="영문+숫자+특수문자 (8~20글자)" data-placeholder="영문+숫자+특수문자 (8~20글자)" />
			    </div>
			    <span id="newPassword_confirm" class="forMargin"></span>
			    <div>
			    	<label for="user_email" class="labelCss">비밀번호 확인</label><br>
			    	<input type="password" id="mPwd_confirm" name="mPwd_confirm"
			    	placeholder="새로운 비밀번호와 동일하게 입력하세요." data-placeholder="새로운 비밀번호와 동일하게 입력하세요." />
			    </div>
			    <span id="confirm_pwd" class="forMargin"></span>
			    <div>
			    	<label for="user_email" class="labelCss">인증번호</label><br>
			    	<input type="text" id="inputMathoNum" name="MAthoNum"
			    	placeholder="인증번호를 입력하세요." data-placeholder="인증번호를 입력하세요." />
			    </div>
			    <input type="button" value="비밀번호 변경하기" id="updatePwdBtn" class="updateBtn"/>
		    </form>
    </div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>

