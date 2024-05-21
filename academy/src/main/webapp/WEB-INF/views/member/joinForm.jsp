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
			
		placeholderEvent($('#email'));
		placeholderEvent($('#inputPwd'));
		placeholderEvent($('#inputPwd_confirm'));
		
		// 아이디 정규식
		$('#email').on('input', function(){
			validateId();
		});
		
		// 비밀번호 정규식
		$('#inputPwd').on('input', function() {
		    validatePassword();
		});

		// 비밀번호 확인
		$('#inputPwd, #inputPwd_confirm').on('input', function() {
                validatePassword2();
            });
            
		// 핸드폰번호 정규식
		$('#inputMobile').on('input', function() {
            formatPhoneNumber($(this));
        }).on('keydown', function(event) {
            enforceInput(event);
        });
        
		
		// 아이디 정규식
		
		function validateId() {
		    const email = $('#email').val();
		    const idRegex = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/
		    const emailInput = $('#email');
		    const messageSpan = emailInput.parent().find('span');
		
		    if (email) {
		        if (idRegex.test(email)) {
		            emailInput.removeClass('error-bg').addClass('success-bg');
		            messageSpan.removeClass('error-message').addClass('success-message');
		            messageSpan.text('사용 가능한 아이디입니다.');
		        } else {
		            emailInput.removeClass('success-bg').addClass('error-bg');
		            messageSpan.removeClass('success-message').addClass('error-message');
		            messageSpan.text('사용 불가능한 아이디입니다.');
		        }
		    } else {
		        emailInput.removeClass('error-bg success-bg');
		        messageSpan.removeClass('error-message success-message');
		        messageSpan.text('');
		    }
		}
        
        // 비밀번호 정규식
        function validatePassword() {
		    const password = $('#inputPwd').val();
		    const passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/
		    const passwordInput = $('#inputPwd');
		    const messageSpan = passwordInput.parent().find('span');
		
		    if (password) {
		        if (passwordRegex.test(password)) {
		            passwordInput.removeClass('error-bg').addClass('success-bg');
		            messageSpan.removeClass('error-message').addClass('success-message');
		            messageSpan.text('사용 가능한 비밀번호입니다.');
		        } else {
		            passwordInput.removeClass('success-bg').addClass('error-bg');
		            messageSpan.removeClass('success-message').addClass('error-message');
		            messageSpan.text('사용 불가능한 비밀번호입니다.');
		        }
		    } else {
		        passwordInput.removeClass('error-bg success-bg');
		        messageSpan.removeClass('error-message success-message');
		        messageSpan.text('');
		    }
		}
        
        // 비밀번호 확인
        function validatePassword2() {
            const password = $('#inputPwd').val();
            const confirmPassword = $('#inputPwd_confirm').val();
            const confirmPasswordInput = $('#inputPwd_confirm');
            const messageSpan = confirmPasswordInput.next('br').next('span');

            if (password !== confirmPassword && confirmPassword !== "") {
                confirmPasswordInput.removeClass('success-bg').addClass('error-bg');
                messageSpan.removeClass('success-message').addClass('error-message');
                messageSpan.text('비밀번호가 일치하지 않습니다.');
            } else if (password === confirmPassword && confirmPassword !== "") {
                confirmPasswordInput.removeClass('error-bg').addClass('success-bg');
                messageSpan.removeClass('error-message').addClass('success-message');
                messageSpan.text('비밀번호가 일치합니다.');
            } else {
                confirmPasswordInput.removeClass('error-bg success-bg');
                messageSpan.removeClass('error-message success-message').text('');
            }
        }
        
        // 핸드폰 번호 정규식
        function formatPhoneNumber(input) {
            // 현재 입력된 값에서 숫자만 추출
            let value = input.val().replace(/\D/g, '');
            
            // 3-4-4 형식으로 하이픈 삽입
            if (value.length > 3 && value.length <= 7) {
                value = value.replace(/(\d{3})(\d{1,4})/, '$1-$2');
            } else if (value.length > 7) {
                value = value.replace(/(\d{3})(\d{4})(\d{1,4})/, '$1-$2-$3');
            }
            
            // 하이픈을 포함한 전체 길이가 13자리를 초과하는지 확인
            if (value.length > 13) {
                alert("더이상 입력할 수 없습니다.");
                value = value.slice(0, 13);
            }
            
            input.val(value);
        }
        // 핸드폰 번호 정규식
        function enforceInput(event) {
            const key = event.key;
            
            // 백스페이스를 제외한 키가 눌릴 때
            if (key !== 'Backspace') {
                // 숫자가 아닌 키가 눌렸을 때
                if (!/[\d]/.test(key)) {
                    alert("숫자만 입력할 수 있습니다.");
                    event.preventDefault();
                }
            }
        }
		
			
	}) // $종료
</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div id="wrap2">
	    <h2 class="joinCss">회원가입</h2>
	    <div>
	    	<form id="joinForm">
	    		<div>
	    			<label for="user_email" class="labelCss">아이디</label><br>
	    			<input type="text" id="email" name="email" 
	    			placeholder="영문+숫자 (8글자~20글자)" data-placeholder="영문+숫자 (8글자~20글자)"/><br>
	    			<span></span>
	    		</div>
	    		<div>
	    			<label for="user_pwd" class="labelCss">비밀번호</label><br>
	    			<input type="password" id="inputPwd" name="MPwd" 
	    			placeholder="영문+숫자+특수문자 (8~20글자)" data-placeholder="영문+숫자+특수문자 (8~20글자)" autoComplete="off"/><br>
	    			<span></span>
	    		</div>
	    		<div>
	    			<label for="user_pwd" class="labelCss">비밀번호 확인</label><br>
	    			<input type="password" id="inputPwd_confirm" name="mPwd_confirm" 
	    			placeholder="비밀번호 확인" data-placeholder="비밀번호 확인" autoComplete="off"/><br>
	    			<span></span>
	    		</div>
	    		<div>
	    			<label for="user_name" class="labelCss">이름</label><br>
	    			<input type="text" id="inputName" name="name" />
	    		</div>
	    		<div>
	    			<label id="radioCss">
	    			<span>남성</span>
					<input type="radio" name="gender" value="M" checked class="radioCCC"/>
					</label>
					<label id="radioCss2">
					<span>여성</span>
					<input type="radio" name="gender" value="F" checked class="radioCCC"/>
					</label>
	    		</div>
	    		<div>
		            <label for="user_mobile" class="labelCss">연락처</label><br>
		            <input type="text" id="inputMobile" name="mobile" />
		        </div>
		        <div>
	    			<label for="user_mAthoNum" class="labelCss">인증번호</label><br>
	    			<input type="text" id="inputMathoNum" name="MAthoNum" />
	    		</div>
	    	</form>
	    </div>
    </div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>

