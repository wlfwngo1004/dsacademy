<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<link rel="stylesheet" type="text/css" href="/include/css/header.css">
<link rel="stylesheet" type="text/css" href="/include/css/footer.css">
<link rel="stylesheet" type="text/css" href="/include/css/main.css">

<title>DS ACADEMY</title>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>

<script type="text/javascript">

</script>
</head>
<body>
	<div class="wrap">
	<header>
		<div class="twelve">
	        <a href="/" class="logo-link"><h1>DS ACADEMY</h1></a>
	          <nav id="nav1">
			    <ul>
			      <li><a href="/">홈</a></li>
			      <li><a href="/">강좌</a></li>
			      <li><a href="/">Q&A</a>
			      <li><a href="/">학원위치</a></li>
			      <c:if test="${empty sessionScope.currentUser}">
                      <li><a href="/member/loginForm">로그인</a></li>
                      <li><a href="/">회원가입</a></li>
                  </c:if>
                  <c:if test="${not empty sessionScope.currentUser}">
                      <li><a href="/member/naver/invalidate">로그아웃</a></li>
                      <li><a href="/">마이페이지</a></li>
                  </c:if>
			    </ul>
			  </nav>
    	</div>
	</header>
	
	<div id="profileInfo"></div>
