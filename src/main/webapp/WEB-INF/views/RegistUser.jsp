<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Regist User</title>

	<script>
		$(document).ready(function(){
			
			$("#BtnRegistAccount").click(function(){
				var input_id = $("#AdminID").val();
				var mac_address = $("#MacAddress").val();
				var pw = $("#PassWord").val();
				
				var param = {"adminid":input_id, "macaddress":mac_address, "password":pw};
				
				$.ajax({
					
					type: "post",
					data: param,
					url: "/CSP/RegistAccount",
					
					success: function(data){
						console.log(data);
						if(data != ""){
							location.href = "${path}/CSP";	
						}
						else{
							alert("중복되는 id가 존재합니다");
						}
					}
					
				});
			});
			
		});
	</script>

</head>
<body>
	
	<div class="container">
		<table class="table table-bordered">
			<tbody>
				
					<tr>
						<th>Admin ID: </th>
						<td><input type="text" style="width:100%;" id="AdminID" class="form-control"/></td>
					</tr>
					<tr>
						<th>Password: </th>
						<td><input type="password" style="width:100%;" id="PassWord" class="form-control"/></td>
					</tr>
					<tr>
						<th>Mac Address: </th>
						<td><input type="text" style="width:100%;" id="MacAddress" class="form-control"/></td>
					</tr>
					<tr>
		                <td colspan="2">
		                 	<button type="button" id="BtnRegistAccount">계정등록</button>
		                </td>
           			</tr>
				
			</tbody>
		</table>
	</div>    
	
	
	
</body>
</html>