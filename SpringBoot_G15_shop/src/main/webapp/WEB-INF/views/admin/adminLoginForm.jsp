<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="admin/admin.css">
</head>
<body>
<div id="wrap">
        <header>
        <div id="logo">
                <a href="admin"><img src="images/admin/bar_01.gif" style="float:left;"><img src="images/admin/text.gif"></a>
                </div>
        </header>
        <div class="clear"></div>
        <article><div id="loginform">
                <form name="frm" method="post" action="adminLogin">
                <table>
                        <tr><td>아 이 디</td><td><input type="text" name="workId" size="10"></td></tr>
                        <tr><td>비밀번호</td><td>        <input type="password" name="workPwd" size="10"></td></tr>
                       <tr align="center" ><td  colspan="2">
                               <input class="btn" type="submit" value="업무 로그인" onClick="return worker_check();"><br><br>
                               <h4 style="color:red">${message}</h4></td></tr>
                </table>
                </form>
        </div></article>
</div>

</body>
</html>