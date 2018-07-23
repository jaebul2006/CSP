<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="./static/bootstrap/js/Chart.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>  
<title>유저등록</title>
</head>
<body>
	<script>
	
		var _valid_id = false;
	
		function check_validId()
		{	
			var input_id = $('#check_id').val();
			var param = {"id":input_id};
			var user_id;
			
			user_id = document.getElementById("valid_id");
			
			$.ajax({
				type:'post',
				data: param,
				dataType:'json',
				url:'/CSP/check_validId',
				success: function(data){
					
					console.log(data);
					
					if(data == true && input_id != ''){
						user_id.innerHTML = "사용가능한 id";
						user_id.style.color = "blue";
						_valid_id = true;
					}
					else{
						user_id.innerHTML = "사용불가능한 id";
						user_id.style.color = "red";
						_valid_id = false;
					}
				}
			});
		}
		
		function IsSamePass()
		{
			var first_pass = $('#pass_first').val();
			var second_pass = $('#pass_second').val();
			
			if(first_pass == second_pass && first_pass != '' && second_pass != ''){
				return true;
			}
			else{
				return false;
			}
		}
		
		function IsEmptyField()
		{
			console.log(groupuser);
			
			if($('#check_id').val() == undefined || $('#pass_first').val() == undefined || $('#pass_second').val() == undefined
					|| $('#user_name').val() == undefined || $('#check_id').val() == '' || $('#pass_first').val() == ''
					|| $('#pass_second').val() == '' || $('#user_name').val() == '' || groupuser == "noselect"){
				return false;
			}
			else{
				return true;	
			}
		}
		
		function doValidForm()
		{
			console.log(_valid_id);
			console.log(IsSamePass());
			console.log(IsEmptyField());
			
			if(_valid_id && IsSamePass() && IsEmptyField()){
				document.form1.submit();
			}
			else{
				window.alert("등록폼에 잘못된 값이 있습니다.");
			}
		}
			
	</script>

	<form name="homebtn" action="/CSP"><input type="submit" value="홈으로"></form>

	<h2>유저등록</h2>
	<form name="form1" method="post" action="user_registmember_process">
		<table>
			<tr height="40px">
				<td>유저id</td>
				<td><input type="text" name="id" oninput="check_validId()" id="check_id"></input></td>
				<td><name="valid_id" id="valid_id"></td>
				<td>(대소문자구별x, 영어만 가능)</td>
			</tr>
			<tr height="40px">
				<td>패스워드</td>
				<td><input type="password" name="pw" id="pass_first"></input></td>
			</tr>
			<tr height="40px">
				<td>패스워드 확인</td>
				<td><input type="password" name="pw_confirm" oninput="IsSamePass()" id="pass_second"></input></td>
			</tr>
			<tr height="40px">
				<td>이름</td>
				<td><input type="text" name="user_name" id="user_name"></input></td>
			</tr>
			<tr height="40px">
				<td>그룹유저id</td>
				<!--  <td><input type="text" name="groupuser"></input></td>-->
				<td>
					<select id="mySel" name="groupuser">
						<option value="noselect">선택없음</option>
						<c:forEach items="${gl}" var="gl">
							<option value="${gl.id}">${gl.id}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			
		</table>
		<table>
			<tr>
				<td align="center"><input id="regist" type="button" value="등록" onclick="doValidForm();"></input></td>
				<td align="center"><input type="reset" value="리셋"></input></td>
			</tr>
		</table>
		<!-- <input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}" ></input> -->
	</form>
	
	<script>
		var groupuser = "noselect";
	
		var sel = document.getElementById('mySel');
		sel.onchange = function()
		{
			//console.log(this.options[this.selectedIndex].value);
			groupuser = this.options[this.selectedIndex].value;
			console.log(groupuser);
		}
	</script>
	
</body>
</html>