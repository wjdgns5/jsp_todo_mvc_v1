<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
<!--  // http://localhost:8080/mvc/user/signIn -->
	<h1>로그인 JSP 파일 입니다.</h1>
	
	<!-- 회원 가입 성공 메세지 출력 -->
	<%
		// String errorMessage = (String)request.getAttribute("message");
		String success = (String)request.getParameter("message");
		if(success != null){
	%>	
		<p style="color:red"> <%=success %> </p>	
		
	<% } %>
	<!--  절대 경로로 사용 해보기 -->
	<form action="/mvc/user/signIn" method="POST">
		<label for="username">사용자 이름 : </label>
		<input type="text" id="username" name="username" value="야스오1">
		
		<label for="password">비밀번호</label>
		<input type="password" id="password" name="password" value="1234">
		
		<button type="submit">로그인</button>
	</form>
	
	
</body>
</html>