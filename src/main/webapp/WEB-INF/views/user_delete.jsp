<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="./static/bootstrap/js/Chart.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>유저삭제</title>
</head>
<body>
	<h2>유저삭제: ${user.ID}</h2>
	<form name="form1" method="post" action="/index">
		<table>
			<tr height="40px">
				<td>패스워드</td>
				<td><input type="password" name="pass" id="pass"></input></td>
			</tr>
			<tr height="40px">
				<td>패스워드확인</td>
				<td><input type="password" name="passconfirm" id="passconfirm"></input></td>
			</tr>
		</table>
		<table>
			<tr>
				<td align="center"><input id="delete" type="button" value="삭제" onclick="doValidUserDelete('${user.ID}');"></input></td>
			</tr>
		</table>
		<!-- <input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}" ></input> -->
	</form>
	
	<script>	
		function doValidUserDelete(user_id)
		{	
			var input_id = user_id;
			var input_pass = $('#pass').val();
			var input_passconfirm = $('#passconfirm').val();
			
			if(input_pass != input_passconfirm){
				window.alert("비밀번호가 같지 않습니다");
				return;
			}
			
			var param = {"id":input_id, "pass":input_pass};
			
			$.ajax({
				type:'post',
				data: param,
				dataType:'json',
				url:'/CSP/group_delete_process',
				success: function(data){
					if(data == true){
						window.alert("삭제가 완료되었습니다.");
						document.form1.submit();
					}
					else{
						window.alert("등록된 그룹계정 또는 비밀번호가 잘못되었습니다.");
					}
				}
			});
		}
	</script>
	
</body>
</html>