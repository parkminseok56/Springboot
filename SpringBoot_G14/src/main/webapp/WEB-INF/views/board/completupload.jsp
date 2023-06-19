<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<script type="text/javascript">
            opener.frm.imgfilename.value='${image}'; // 전송될 히든 태그에 이름 삽입
            opener.document.getElememtById('image').innerHTML = '${image}';
            opener.document.getElememtById('previewimg').setAttribute('src','/upload'/ + '${image}');
            opener.document.getElememtById('previewimg').style.display='inline';


</script>



</body>
</html>