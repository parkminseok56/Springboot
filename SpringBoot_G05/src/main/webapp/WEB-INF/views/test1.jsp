<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1><% out.println("#hello lombook"); %></h1>
<h1>당신의 아이디는 ${ memberdto.id } 이므니다.</h1>
<h1>당신의 이름은 ${ memberdto.name } 이므니다.</h1>

</body>
</html>