<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/include/css/findIdPwdForm.css">

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
		
		placeholderEvent($('.name'));
		placeholderEvent($('.email'));
		placeholderEvent($('.MAthoNum'));
		
		$('.MAthoNum').on('input', function() {
	        var value = $(this).val();
	        if(value.match(/[^0-9\t]/g)) {
	            alert("숫자만 입력할 수 있습니다.");
	            $(this).val('');
	        }
	    });
		
		// Id 찾기
		$('#findIdBtn').click(function(){
			let name = $('#name').val();
			let mAthoNum = $('#MAthoNum').val();
			console.log(name, MAthoNum);
			
			if(!chkData('#name', "이름을")) return;
			else if(!chkData('#MAthoNum', "인증번호를")) return;
			else{
				$.ajax({
					url:"/member/findId",
					type:"POST",
					data:{
						name:name,
						mAthoNum:mAthoNum
					},
					dataType: 'json',// 응답 데이터를 제이슨 형식으로 받아오자!
					success: function(data){
						if(data.status === "성공"){
							$('#successId').text('아이디 : ' + data.userId);
						}else{
							$('#successId').html(data.message);
						}
					}
				})
			}
			
		}); // Id 찾기 ajax 끝
		
		// Password 찾기
		$('#findPwdBtn').click(function(){
			let name = $('#name2').val();
			let email = $('#email2').val();
			let mAthoNum = $('#MAthoNum2').val();
			console.log(name, email, mAthoNum);
			if(!chkData('#name2', "이름을")) return;
			else if(!chkData('#email2', "아이디를")) return;
			else if(!chkData('#MAthoNum2', "인증번호를"))return;
			else{
				$.ajax({
					url:"/member/findPassword",
					type:"POST",
					data:{
						name:name,
						email:email,
						mAthoNum:mAthoNum
					},
					dataType:'json',
					success: function(data){
						if(data.status === "성공"){
							$('#successPassword').text('비밀번호 : ' + data.userPassword);
							$('#successPassword').append('<br><input type="button" id="moveLoginForm"  value = "로그인 하기" class="successBtn"/>');
							$('#successPassword').append('<input type="button" id="moveUpdatePwdForm"  value = "새로운 비밀번호로 변경하기" class="successBtn"/>');
							
							$('#moveLoginForm').click(function(){
								window.location.href="/member/loginForm";
							})
							
							$('#moveUpdatePwdForm').click(function(){
								window.location.href="/member/updatePwdForm";
							})
							
						}else{
							$('#successPassword').html(data.message);
						}
					}
				})
			}
		}) // Password 찾기 끝
		
		
	})
</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div id="wrap2">
    <div class="form-container" id="findIdContainer">
        <h2>아이디 찾기</h2>
        <form id="findIdForm">
            <div>
                <label for="user_name" class="labelCss">이름</label><br>
                <input type="text" id="name" name="name" class="name"
                placeholder="홍길동" data-placeholder="홍길동"/>
            </div>
            <div>
                <label for="user_mAthoNum" class="labelCss">인증번호</label><br>
                <input type="text" id="MAthoNum" name="MAthoNum" class="MAthoNum"
                placeholder="본인의 인증번호를 입력해주세요" data-placeholder="본인의 인증번호를 입력해주세요"/>
            </div>
            <span id = "successId"></span>
            <input type="button" value="아이디 확인" id="findIdBtn" class="findBtn"/>
        </form>
    </div>
    <div class="form-container" id="findPwdContainer">
        <h2>비밀번호 찾기</h2>
        <form id="findPwdForm">
            <div>
                <label for="user_name" class="labelCss">이름</label><br>
                <input type="text" id="name2" name="name" class="name"
                placeholder="홍길동" data-placeholder="홍길동"/>
            </div>
            <div>
                <label for="user_email" class="labelCss">아이디</label><br>
                <input type="text" id="email2" name="email" class="email"
                placeholder="아이디 또는 이메일을 입력해주세요." data-placeholder="아이디 또는 이메일을 입력해주세요."/>
            </div>
            <div>
                <label for="user_mAthoNum" class="labelCss">인증번호</label><br>
                <input type="text" id="MAthoNum2" name="MAthoNum" class="MAthoNum"
                placeholder="본인의 인증번호를 입력해주세요" data-placeholder="본인의 인증번호를 입력해주세요"/>
            </div>
            <span id="successPassword"></span>
            <input type="button" value="비밀번호 확인" id="findPwdBtn" class="findBtn"/>
        </form>
    </div>
	</div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>
