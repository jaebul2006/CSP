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
	
	<!--  <a href="${path}/CoachingBoard/CoachingBoardList">게시판</a> -->

	<script>
		$(document).ready(function(){
			/*$("#btnWrite").click(function(){
				location.href = "${path}/CoachingBoard/CoachingBoardWrite"
			});*/
		});
		
		function list(page){
			location.href = "${path}/CoachingBoard/TCoachingBoardList?&curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
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
				<li class="active"><a href="/CSP/DashBoard1">코칭 대시보드</a></li>
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
							<li><a href="/CSP/CoachingBoard/TCoachingBoardListHome?id=${pageContext.request.remoteUser}">Home</a></li>
							<c:choose>
								<c:when test="${map.content_type == 'ENTSurgery'}">
									<li class="active"><a href="/CSP/CoachingBoard/TCoachingBoardList?content_type=ENTSurgery">이비인후과 수술</a></li>	
								</c:when>
								<c:otherwise>
									<li><a href="/CSP/CoachingBoard/TCoachingBoardList?content_type=ENTSurgery">이비인후과 수술</a></li>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${map.content_type == 'HipSurgery'}">
									<li class="active"><a href="/CSP/CoachingBoard/TCoachingBoardList?content_type=HipSurgery">고관절 치환술</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="/CSP/CoachingBoard/TCoachingBoardList?content_type=HipSurgery">고관절 치환술</a></li>
								</c:otherwise>
							</c:choose>
							<li role="presentation"><a href="#message" aria-controls="messages" role="tab" data-toggle="tab">ETC 수술1</a></li>
						</ul>
				
			<c:forEach var="row" items="${map.list}">
				<c:choose>
					<c:when test="${row.isshow == 'y'}">
						<!--  <div class="col-x1-3 col-sm-12 col-md-6 col-lg-6 mt-4"> -->
						<div class="col-md-3">
							<div class="card">
								<img class="card-img-top" src="../assets/img/vr.jpg">
								<!--  <img class="card-img-top" src="../assets/${row.fileName}Thumbnail-1.jpg" onerror="javascript:this.src='../assets/img/vr.jpg'"> -->
								
								<div class="card-block">
									<h4 class="card-title mt-3">${row.title}
										<c:if test="${row.recnt > 0}">
											<span style="color: red;">(${row.recnt})</span>
										</c:if> 
									</h4>
									<!--  <div class="meta">
										<a href="#">${row.writer}</a>
									</div> -->
									<div class="card-text"><fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
									
								</div>
								<div class="card-footer">
									<button class="btn btn-secondary btn-sm" onclick="window.location.href='${path}/CoachingBoard/TCoachingBoardView?bno=${row.bno}&curPage=${map.boardPager.curPage}&searchOption=${map.searchOption}&keyword=${map.keyword}'">show</button> 
								</div>
							</div>
						</div>
					</c:when>
				</c:choose>
			</c:forEach>
				</div>
			</div>
		</div>
		
	</div>


	<form name="form1" method="post" action=${path}/CoachingBoard/TCoachingBoardList">  
		<select name="searchOption">
			<option value="all" <c:out value="${map.searchOption == 'all' ? 'selected' : ''}" /> >제목+이름+제목</option>
			<option value="user_name" <c:out value="${map.searchOption}" /> >이름</option>
			<option value="content" <c:out value="${map.searchOption == 'content' ? 'selected' : ''}" /> >내용</option>
			<option value="title" <c:out value="${map.searchOption == 'title' ? 'selected' : ''}" /> >제목</option>
		</select>
		<input name="keyword" value="${map.keyword}">
		<input type="submit" class="btn btn-primary" value="조회">
		<button type="button" class="btn btn-info" id="btnWrite">글쓰기</button>
		<input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}" ></input>
	</form>
		
	${map.count} 개의 게시물이 있습니다
	<br>
		<!--  
		<tr>
			<td colspan="5">
				<c:if test="${map.boardPager.curBlock > 1}">
					<a href="javascript:list('1')">[처음]</a>
				</c:if>
				<c:if test="${map.boardPager.curBlock > 1}">
					<a href="javascript:list('${map.boardPager.prevPage}')">[이전]</a>
				</c:if>
				
				<c:forEach var="num" begin="${map.boardPager.blockBegin}" end="${map.boardPager.blockEnd}">
					<c:choose>
						<c:when test="${num == map.boardPager.curPage}">
							<span style="color: red">${num}</span>&nbsp;
						</c:when>
						<c:otherwise>
							<a href="javascript:list('${num}')">${num}</a>&nbsp;
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${map.boardPager.curBlock <= map.boardPager.totBlock}">
					<a href="javascript:list('${map.boardPager.nextPage}')">[다음]</a>
				</c:if>
				<c:if test="${map.boardPager.curPage <= map.boardPager.totPage}">
					<a href="javascript:list('${map.boardPager.totPage}')">[끝]</a>
				</c:if>
			</td>
		</tr>
		-->
	
	<!-- 이쁜 스타일의 페이징 -->
	<div class="container">
		<ul class="pagination pagination-lg">
			<!--  <li><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li> -->
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