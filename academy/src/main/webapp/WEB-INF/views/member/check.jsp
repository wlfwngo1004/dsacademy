<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript" src="/include/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="/include/css/atho.css">

<script type="text/javascript">

</script>
	
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div class="container">
	    <form id="myForm">
	        <div>
	            <input type="text" id="m_atho_num" name="m_atho_num">
	            <button type="button" id="loginBtn">인증번호 확인</button>
	        </div>
	    </form>
	</div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>

