<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 
<div id="header">
	<h1><a href="/mysite2/main">MySite</a></h1>
	<!-- 로그인 실패 or 로그인 전 -->
	<c:if test="${empty sessionScope.authUser}">
		<ul>
			<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
			<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
		</ul>
	</c:if>
		<!-- 로그인 성공 시 -->
	<c:if test="${!empty sessionScope.authUser}">
		<ul>
			<li>${sessionScope.authUser.name} 님 안녕하세요^^</li>
			<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
			<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
		</ul>
	</c:if>
			
</div>