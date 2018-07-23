<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:1920px;">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>코칭 보드</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
	<link rel="stylesheet" href="../assets/css/styles.min.css">
	<link rel="stylesheet" type="text/css" href="../static/site/css/styles.css" />
	<link rel="stylesheet" type="text/css" href="../assets/CoachingList/Style.css" />
	  
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script>
		function list(page){
			location.href = "${path}/CoachingBoard/TCoachingBoardList?id=${pageContext.request.remoteUser}&curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
		}
	</script>
</head>

<body style="width:1920px;">

	<div id="header">
		<div id="top-header">
			<div id="company-logo">
				<h1><img src="../static/site/images/logo-1.png" alt="BIZZ GROUP" id="logo" /></h1>
			</div>
			<ul id="main-navigation">
				<li><a href="/CSP">홈</a></li>
				<li><a href="/CSP/ProductInfo">제품소개</a></li>
				<li class="active"><a href="/CSP/DashBoard1?id=${pageContext.request.remoteUser}">코칭 대시보드</a></li>
				<li><a href="#">커뮤니티</a></li>
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
	</div>

	<div class="container">
	
		<div class="row">
			<div class="col-md-12">
				<div class="card">
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Home</a></li>
						<li><a href="/CSP/CoachingBoard/TCoachingBoardList?id=${pageContext.request.remoteUser}&content_type=ENTSurgery">이비인후과 수술</a></li>
						<li><a href="/CSP/CoachingBoard/TCoachingBoardList?id=${pageContext.request.remoteUser}&content_type=HipSurgery">고관절 치환술</a></li>
						<li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">ETC 수술</a></li>
					</ul>
				
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="home">
						<img src="../static/site/images/operation.jpg" class="photo" alt="" style="width:100%; height:70%; opacity:0.6;" />
						</div>
					</div>	
				</div>
				
				

			</div>
			
			
		</div>
	</div>
	
	
</body>
</html>