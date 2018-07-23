<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="./include/header.jsp" %>

	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
	<link rel="stylesheet" href="../assets/css/styles.min.css">
	<link rel="stylesheet" type="text/css" href="../static/site/css/styles.css" />
	<link rel="stylesheet" type="text/css" href="../assets/CoachingList/Style.css" />
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<!--  <script type="text/javascript" src="${path}/include/js/common.js"></script> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글</title>
</head>

<body>
	
	<div id="header">
		<div id="top-header">
			<div id="company-logo">
				<h1><img src="../static/site/images/logo-1.png" alt="BIZZ GROUP" id="logo" /></h1>
			</div>
			<ul id="main-navigation">
				<li><a href="/CSP">홈</a></li>
				<li><a href="#">제품소개</a></li>
				<li class="active"><a href="/CSP/DashBoard1?id=${pageContext.request.remoteUser}">코칭 대시보드</a></li>
				<li><a href="#">커뮤니티</a></li>
				<li><a href="contacts.html">제품문의</a></li>
			</ul>
			<div id="userInfo" style="position:relative; width:50%; left:70%; top:52%;">
				<c:choose>
					<c:when test="${session_login != null}">
						<p> 안녕하세요. <b><c:out value="${pageContext.request.remoteUser}" /></b></p>
					</c:when>
				</c:choose>
			</div>
			<!--  
			<div id="add-navigation">
				<a href="index.html"><img src="../static/site/images/home-icon.png" alt="" /></a>
				<a href="contacts.html"><img src="../static/site/images/mail-icon.png" alt="" /></a>
				<a href="sitemap.html"><img src="../static/site/images/misc-icon.png" alt="" /></a>
			</div>
			-->
			<div class="clear"></div>
		</div>
	</div>
	
	<c:choose>
		<c:when test="${dto.isshow == 'y'}">
			<div class="container">
				<table class="table table-bordered">
					<tbody>
						<c:url var="downloadUrl" value="/download" />
						<form name="form-inline" action="${downloadUrl}" method="get">
							<tr>
								<th>제목: </th>
								<td>${dto.title}</td>
							</tr>
							<tr>
								<th>내용: </th>
								<td>${dto.content}</td>
							</tr>
							<tr>
								<th>영상파일: </th>
								<td> ${dto.fileName}
								<input type="submit" value="다운로드" class="btn btn-info pull-right" />
								<input type="hidden" name="filepath" value="${dto.fileName}" />
								</td>
							</tr>
							<tr>
								<th>영상: </th>
								<td> 
									<video width="640" height="480" controls autoplay="autoplay">
										<source src="${pageContext.request.contextPath}/assets${dto.fileName}" type="video/mp4">
									</video>
								</td>
							</tr>
							<tr>
								<th>등록일자: </th>
								<td><fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
							<tr>
								<th>조회수: </th>
								<td>${dto.viewcnt}</td>
							</tr>
							<tr>
				                <td colspan="2">
				                 	<button type="button" id="btnList" class="btn btn-primary pull-right">리스트</button>
				                </td>
		           			</tr>
						</form>
					</tbody>
				</table>
			</div>
		</c:when>
	</c:choose>
	    
	<!--  
	<c:choose>
		<c:when test="${dto.isshow == 'y'}">	
			<c:url var="downloadUrl" value="/download" />
			<form class="form-inline" action="${downloadUrl}" method="get">
				<input type="submit" value="다운로드" />
				<input type="hidden" name="filepath" value="${dto.fileName}" />
			</form>
		</c:when>
		<c:otherwise>	
			삭제된 게시글 입니다.
		</c:otherwise>
	</c:choose>
	-->
	
	<div id="listReply"></div>
	
	<script>
	$(document).ready(function(){
		listReplyRest("1");	
		
		$("#btnList").click(function(){
			location.href="${path}/CoachingBoard/CoachingBoardListHome";
		});
		
	});
	
	
	function listReplyRest(num)
	{
		$.ajax({
			type: "get",
			url: "${path}/CoachingBoardReply/list/${dto.bno}/" + num,
			success: function(result){
				console.log(result);
				$("#listReply").html(result);
			},
			error :	function(request, status, error){
				console.log(request.responseText);
			}
		});	
	}
	</script>
</body>

</html>