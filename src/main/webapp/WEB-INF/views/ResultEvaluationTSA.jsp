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
	<link rel="stylesheet" href="./assets/css/styles.min.css">
	<link rel="stylesheet" type="text/css" href="./static/site/css/styles.css" />
	<link rel="stylesheet" type="text/css" href="./assets/CoachingList/Style.css" />
	  
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<script>
		$(document).ready(function(){
			
		});
		
		function list(page){
			location.href = "${path}/ResultEvaluationTSA?id=${pageContext.request.remoteUser}&curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}" + "&content_type=${map.content_type}";
		}
	</script>
</head>

<body style="width:1920px;">

	<div id="header">
		<div id="top-header">
			<div id="company-logo">
				<h1><img src="./static/site/images/logo-1.png" alt="BIZZ GROUP" id="logo" /></h1>
			</div>
			<ul id="main-navigation">
				<li><a href="/CSP">홈</a></li>
				<li><a href="/CSP/ProductInfo">제품소개</a></li>
				<li class="active"><a href="/CSP">수술 평가 갤러리</a></li>
				<li><a href="/CSP/DashBoard1?id=${pageContext.request.remoteUser}">코칭 대시보드</a></li>
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
					<li><a href="/CSP/ResultEvaluationHome?id=${pageContext.request.remoteUser}">
					<h4 class="glyphicon glyphicon-home"></h4><br/>메인
					Home</a></li>
					<c:choose>
						<c:when test="${map.content_type == 'ENTSurgery'}">
							<li class="active"><a href="/CSP/ResultEvaluationTSA?id=${pageContext.request.remoteUser}&content_type=ENTSurgery">
							<h4 class="glyphicon glyphicon-check"></h4><br/>
							TSA 수술</a></li>	
						</c:when>
						<c:otherwise>
							<li><a href="/CSP/ResultEvaluationTSA?id=${pageContext.request.remoteUser}&content_type=ENTSurgery">
							<h4 class="glyphicon glyphicon-check"></h4><br/>
							TSA 수술</a></li>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${map.content_type == 'HipSurgery'}">
							<li class="active"><a href="#">
							<h4 class="glyphicon glyphicon-check"></h4><br/>
							OS 수술</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="#">
							<h4 class="glyphicon glyphicon-check"></h4><br/>
							OS 수술</a></li>
						</c:otherwise>
					</c:choose>
				</ul>
				
				<c:forEach var="row" items="${map.list}">		
					<div class="col-md-3">
						<div class="card">
							<c:choose>
								<c:when test="${row.total_score <= '150'}">
									<img class="card-img-top" src="./assets/img/oper_red.png">
								</c:when>
								<c:when test="${row.total_score <= '215'}">
									<img class="card-img-top" src="./assets/img/oper_yellow.png">
								</c:when>
								<c:otherwise>
									<img class="card-img-top" src="./assets/img/oper_green.png">
								</c:otherwise>
							</c:choose>
							
							<div class="card-block">
								<h4 class="card-title mt-3">${row.upload_date} </h4>
							</div>
							
							<div class="card-text">총점수: ${row.total_score}</div>
							<div class="card-text">중간점수1: ${row.middle_score1}</div>
							<div class="card-text">중간점수2: ${row.middle_score2}</div>
							<div class="card-text">중간점수3: ${row.middle_score3}</div>
							
							<div class="card-footer">
								<button class="btn btn-secondary btn-sm" onclick="window.open('${path}/ResultEvaluationTSAView?id=${row.id}', '수술 평가 갤러리', 'width=1280, height=800, resizable=yes');return false;">결과상세</button> 
							</div>
						</div>
					</div>
				</c:forEach>
			
				</div>
			</div>
		</div>
	</div>

	<form name="form1" method="post" action="${path}/ResultEvaluationTSA">  
		<select name="searchOption">
			<option value="all" <c:out value="${map.searchOption == 'all' ? 'selected' : ''}" /> >제목+이름+제목</option>
			<option value="user_name" <c:out value="${map.searchOption}" /> >이름</option>
			<option value="content" <c:out value="${map.searchOption == 'content' ? 'selected' : ''}" /> >내용</option>
			<option value="title" <c:out value="${map.searchOption == 'title' ? 'selected' : ''}" /> >제목</option>
		</select>
		<input name="keyword" value="${map.keyword}">
		<input type="submit" class="btn btn-primary" value="조회">

	</form>
		
	${map.count} 개의 게시물이 있습니다
	<br>
		
	<!-- 이쁜 스타일의 페이징 -->
	<div class="container">
		<ul class="pagination pagination-lg">
			<c:if test="${map.boardPager.curBlock > 1}">
				<li><a href="javascript:list('1')">처음</a></li>
			</c:if>
			<c:if test="${map.boardPager.curBlock > 1}">
				<li><a href="javascript:list('${map.boardPager.prevPage}')">이전</a></li>
			</c:if>
			
			<c:forEach var="num" begin="${map.boardPager.blockBegin}" end="${map.boardPager.blockEnd}">
				<c:choose>
					<c:when test="${num == map.boardPager.curPage}">
						<li class="active"><a href="#">${num}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="javascript:list('${num}')">${num}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${map.boardPager.curBlock <= map.boardPager.totBlock}">
				<li><a href="javascript:list('${map.boardPager.nextPage}')">다음</a></li>
			</c:if>
			<c:if test="${map.boardPager.curPage <= map.boardPager.totPage}">
				<li><a href="javascript:list('${map.boardPager.totPage}')">끝</a></li>
			</c:if>
		</ul>
	</div>
	
</body>
</html>