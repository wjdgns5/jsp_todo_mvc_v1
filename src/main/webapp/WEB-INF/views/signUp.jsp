<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String eMessage = (String)request.getAttribute("errorMessage");
		out.println(eMessage);
	%>

	<h1>회원가입 JSP 파일 입니다.</h1>
</body>
</html>