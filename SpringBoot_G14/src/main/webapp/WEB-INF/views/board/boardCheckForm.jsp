<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="/css/board.css">
<script type="text/javascript">
   function passCheck(){
      if (document.frm.pass.value.length == 0){
          alert("비밀번호를 입력하세요.");
          return false;
          }
          return true;
       }

</script>
<title>Insert title here</title>
</head>
<body>
<div  align="center">
		<h1>비밀번호 확인</h1>				
	    <form name="frm" method="get" action="boardEdit">
			 
			 
			<table>


</table>
</form>
</div>
</body>
</html>