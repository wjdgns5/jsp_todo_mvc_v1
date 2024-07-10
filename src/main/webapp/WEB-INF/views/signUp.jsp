<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<!--  상대경로 : css/styles.css --> 
<link rel="stylesheet" type="text/css" href="../css/styles.css"> 
</head>
<body>
	<h2>회원가입</h2>
	<!-- 에러 메세지 출력 -->
	<%
		// String errorMessage = (String)request.getAttribute("message");
		String errorMessage = (String)request.getParameter("message");
		if(errorMessage != null){
	%>	
		<p style="color:red"> <%=errorMessage %> </p>	
		
	<% } %>
	<!--  절대 경로로 사용 해보기 -->
	<form action="/mvc/user/signUp" method="POST">
		<label for="username">사용자 이름 : </label>
		<input type="text" id="username" name="username" value="야스오1">
		
		<label for="password">비밀번호</label>
		<input type="password" id="password" name="password" value="1234">
		
		<label for="email">이메일</label>
		<input type="text" id="email" name="email" value="abc@nate.com">
		
		<button type="submit">회원가입</button>
	</form>
</body>
</html>