<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="/include/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/include/css/atho.css">

<script type="text/javascript">
	$(function(){
		
		
		
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
		
	})// $ 끝
</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div class="container">
	    <form id="athoForm">
	    	<c:if test="${empty userInfo}">
	        <div>
	            <input type="password" id="mAthoNum" name="mAthoNum">
	            <button type="button" id="loginBtn">인증번호 확인</button>
	        </div>
	        </c:if>
	        <c:if test="${not empty userInfo}">
	        <div>
	            <input type="password" id="mAthoNum" name="mAthoNum" value="${userInfo.MAthoNum}">
	            <button type="button" id="loginBtn">인증번호 확인</button>
	        </div>
	        </c:if>
	    </form>
	</div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>

