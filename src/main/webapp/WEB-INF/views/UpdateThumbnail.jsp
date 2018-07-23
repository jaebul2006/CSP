<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>	
	<script type="text/javascript"></script>
	
	<title>썸네일 추출중</title>

</head>
<body>
	<script>
		setTimeout(function(){
			GoBoardList();
		}, 10000);
		
		function GoBoardList()
		{
			window.location.href = 'http://localhost:8080/CSP/CoachingBoard/CoachingBoardList';	
		} 
	</script>
	
	<p> 동영상에서 썸네일을 추출 중입니다...</p>
	
	<img src="../assets/img/thumb_extract_loading.gif">
</body>
</html>