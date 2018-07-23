<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="./static/site/css/styles.css" />
<script type="text/javascript" src="./static/site/js/jquery/jquery-1.4.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>일반유저등록</title>

	<script>
		$(document).ready(function(){
			
			$("#BtnRegistCommonUser").click(function(){
				var input_id = $("#UserID").val();
				var pw = $("#PassWord").val();
				var nickname = $("#NickName").val();
				
				var param = {"adminid":"${pageContext.request.remoteUser}", 
						"userid":input_id, "password":pw, "nickname":nickname};
				
				$.ajax({
					
					type: "post",
					data: param,
					url: "/CSP/RegistCommonAccount",
					
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
	<div id="top-header">
		<div id="company-logo">
			<h1><img src="./static/site/images/logo-1.png" alt="BIZZ GROUP" id="logo" /></h1>
		</div>
		<ul id="main-navigation">
			<li><a href="index.html">홈</a></li>
			<li><a href="/CSP/ProductInfo">제품소개</a></li>
			<li><a href="/CSP/DashBoard1?id=${pageContext.request.remoteUser}">코칭 대시보드</a></li>
			<li><a href="/CSP/match">커뮤니티</a></li>
			<li><a href="/CSP/LicenseBuy">라이센스구매</a></li>
		</ul>
		<div id="userInfo" style="position:relative; width:50%; left:70%; top:52%;">
			<c:choose>
				<c:when test="${session_login != null}">
					<p> 안녕하세요. <b><c:out value="${pageContext.request.remoteUser}" /></b></p>
				</c:when>
			</c:choose>
		</div>
		<div class="clear"></div>
	</div>
	
	<div class="container">
		<table class="table table-bordered">
			<tbody>
					<tr>
						<th>User ID: </th>
						<td><input type="text" style="width:100%;" id="UserID" class="form-control"/></td>
					</tr>
					<tr>
						<th>Password: </th>
						<td><input type="password" style="width:100%;" id="PassWord" class="form-control"/></td>
					</tr>
					<tr>
						<th>닉네임: </th>
						<td><input type="text" style="width:100%;" id="NickName" class="form-control"/></td>
					</tr>
					<tr>
		                <td colspan="2">
		                 	<button type="button" id="BtnRegistCommonUser">계정등록</button>
		                </td>
           			</tr>
			</tbody>
		</table>
	</div>
</body>

</html>