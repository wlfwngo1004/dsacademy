<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ include file="/WEB-INF/views/header.jsp" %>
	
	<div>
	    <h2>로그인</h2>
	    	<div>
	    		<form id="loginForm">
	    			<div>
	    				<label for="user_id">Id</label>
	    				<input type="text" name="user_id" />
	    			</div>
	    			<div>
	    				<label for="user_pwd">password</label>
	    				<input type="text" name="user_pwd" />
	    			</div>
	    		</form>
	    	</div>
    </div>
	<%@ include file="/WEB-INF/views/footer.jsp" %>
