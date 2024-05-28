<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/include/css/atho.css">

<style>
.container {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	flex-direction: column;
	margin-top: -180px;
}

.form-wrapper {
	text-align: left;
	border: 1px solid #ccc;
	padding: 20px;
	border-radius: 10px;
	width: 300px;
}

.labelCss, #schoolName, #mAthoNum {
	display: block;
	margin-top: 10px;
}

.labelCss {
	font-size: 18px;
	height: 10px;
}
        
.labelCss.school, .labelCss.athoNum {
	margin-top: 20px; /* Adjusted margin-top value for schoolName and mAthoNum */
}
        
#schoolName, #mAthoNum {
	width: 300px; /* Set the width of input fields */
	height: 40px; /* Set the height of input fields */
	box-sizing: border-box; /* Include padding and border in the element's total width and height */
	font-size: 16px; /* Optionally, increase the font size inside the input fields */
	padding: 5px; /* Add some padding inside the input fields */
	margin-top: 0px;
	border: 1px solid #ccc; /* Set the border color to gray */
	border-radius: 5px; /* Set the border radius to make it rounded */
}
        
.radio-group label {
	display: inline-block;
	margin-right: 10px;
}

.radioCCC {
	margin-left: 5px;
	margin-right: 30px;
}

.labelCss span {
	margin-right: 5px;
}

#loginBtn {
	display: block;
	margin-top: 20px;
	width: 100%;
	padding: 10px;
	background-color: #1FBC02;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
}

#loginBtn:hover {
	background-color: #45a049;
}
</style>

<script type="text/javascript">
	$(function(){
		
		placeholderEvent($('#schoolName'));
		
		
		$("#loginBtn").click(function(){
			if(!chkData("#mAthoNum","인증번호를")) return;
			else{
				$("#athoForm").attr({
					"method" : "post",
					"action" : "/member/naver/atho"
				});
				$("#athoForm").submit();
			}
		}); // 인증번호 보내기.
		
		
		
		
		
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
		
		
	})// $ 끝
</script>

	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div class="container">
		<h2 class="loginCss">추가입력사항</h2>
		<div class="form-wrapper">
		    <form id="athoForm">
		    	<c:if test="${empty userInfo}">
		    		<div>
		    			<label for="grade" class="labelCss">학년</label><br>
		    			<label id="radioCss">
		    			<span>1학년</span>
						<input type="radio" name="grade" value="1" checked class="radioCCC"/>
						</label>
						<label id="radioCss2">
						<span>2학년</span>
						<input type="radio" name="grade" value="2" class="radioCCC"/>
						</label>
						<label id="radioCss3">
						<span>3학년</span>
						<input type="radio" name="grade" value="3" class="radioCCC"/>
						</label>
		    		</div>
		    		<div>
		    			<label for="schoolName" class="labelCss school">학교명</label><br>
			            <input type="text" id="schoolName" name="schoolName" placeholder="ex)아트고등학교" data-placeholder="ex)아트고등학교">
			        </div>
			        <div>
			        	<label for="mAthoNum" class="labelCss athoNum">인증번호</label><br>
			            <input type="password" id="mAthoNum" name="mAthoNum">
			        </div>
			        <input type="button" value="인증요청" id="loginBtn"/>
		        </c:if>
		        <c:if test="${not empty userInfo}">
		        <div>
		        	<label for="mAthoNum" class="labelCss">인증번호</label><br>
		            <input type="password" id="mAthoNum" name="mAthoNum" value="${userInfo.MAthoNum}">
		        </div>
		        <input type="button" value="인증요청" id="loginBtn"/>
		        </c:if>
		    </form>
	    </div>
	</div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>

