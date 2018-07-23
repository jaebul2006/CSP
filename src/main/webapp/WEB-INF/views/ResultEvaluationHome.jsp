<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:1920px;">

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>평가 갤러리</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css"> 
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
	<link rel="stylesheet" type="text/css" href="./assets/css/styles.min.css">
	<link rel="stylesheet" type="text/css" href="./static/site/css/styles.css" />
	<link rel="stylesheet" type="text/css" href="./assets/CoachingList/Style.css" />
	  
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="./assets/css/ResultEval.css">

	<script>
		$(document).ready(function() {
		    $("div.bhoechie-tab-menu>div.list-group>a").click(function(e) {
		        e.preventDefault();
		        $(this).siblings('a.active').removeClass("active");
		        $(this).addClass("active");
		        var index = $(this).index();
		        $("div.bhoechie-tab>div.bhoechie-tab-content").removeClass("active");
		        $("div.bhoechie-tab>div.bhoechie-tab-content").eq(index).addClass("active");
		    });
		});
	</script>
	
	<script>
		function list(page){
			//location.href = "${path}/ResultEvaluationHome?id=${pageContext.request.remoteUser}&curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
			location.href = "${path}/ResultEvaluationHome?id=${pageContext.request.remoteUser}&curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
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
				<li ><a href="/CSP">홈</a></li>
				<li><a href="/CSP/ProductInfo">제품소개</a></li>
				<li class="active"><a href="/CSP/ResultEvaluationHome">수술 평가 갤러리</a></li>
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
					
						<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">	
						<h4 class="glyphicon glyphicon-home"></h4><br/> 메인
						Home</a></li>
						
						<li><a href="/CSP/ResultEvaluationTSA?id=${pageContext.request.remoteUser}&content_type=ENTSurgery">
						<h4 class="glyphicon glyphicon-check"></h4><br/>
						TSA 수술</a></li>
						
						<li><a href="/CSP/ResultEvaluationOS?id=${pageContext.request.remoteUser}&content_type=HipSurgery">
						<h4 class="glyphicon glyphicon-check"></h4><br/>
						OS 수술</a></li>
					</ul>
				
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="home">
						<img src="./static/site/images/operation.jpg" class="photo" alt="" style="width:100%; height:70%; opacity:0.6;" /> 
						</div>
					</div>	
				</div>
			</div>
		</div>
	</div>
	
	<!--  
	<div class="container">
	<div class="row">
        <div class="col-lg-5 col-md-5 col-sm-8 col-xs-9 bhoechie-tab-container">
            <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3 bhoechie-tab-menu">
              <div class="list-group">
                <a href="#home" class="list-group-item active text-center">
                  <h4 class="glyphicon glyphicon-home"></h4><br/>Home
                </a>
                <a href="/CSP/ResultEvaluationTSA?id=${pageContext.request.remoteUser}&content_type=ENTSurgery" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-check"></h4><br/>TSA 수술
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-home"></h4><br/>Hotel
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-cutlery"></h4><br/>Restaurant
                </a>
                <a href="#" class="list-group-item text-center">
                  <h4 class="glyphicon glyphicon-credit-card"></h4><br/>Credit Card
                </a>
              </div>
            </div>
            <div class="col-lg-9 col-md-9 col-sm-9 col-xs-9 bhoechie-tab">
                <div class="bhoechie-tab-content active">
                    <center>
                      <h1 class="glyphicon glyphicon-home" style="font-size:14em;color:#55518a"></h1>
                      <h2 style="margin-top: 0;color:#55518a">수술 평가 갤러리 홈</h2>
                    </center>
                </div>
                <div class="bhoechie-tab-content">
                    <center>
                      <h1 class="glyphicon glyphicon-check" style="font-size:12em;color:#55518a"></h1>
                      <h2 style="margin-top: 0;color:#55518a">TSA 결과 평가</h2>
                    </center>
                </div>
    
                <div class="bhoechie-tab-content">
                    <center>
                      <h1 class="glyphicon glyphicon-home" style="font-size:12em;color:#55518a"></h1>
                      <h2 style="margin-top: 0;color:#55518a">Cooming Soon</h2>
                      <h3 style="margin-top: 0;color:#55518a">Hotel Directory</h3>
                    </center>
                </div>
                <div class="bhoechie-tab-content">
                    <center>
                      <h1 class="glyphicon glyphicon-cutlery" style="font-size:12em;color:#55518a"></h1>
                      <h2 style="margin-top: 0;color:#55518a">Cooming Soon</h2>
                      <h3 style="margin-top: 0;color:#55518a">Restaurant Diirectory</h3>
                    </center>
                </div>
                <div class="bhoechie-tab-content">
                    <center>
                      <h1 class="glyphicon glyphicon-credit-card" style="font-size:12em;color:#55518a"></h1>
                      <h2 style="margin-top: 0;color:#55518a">Cooming Soon</h2>
                      <h3 style="margin-top: 0;color:#55518a">Credit Card</h3>
                    </center>
                </div>
            </div>
        </div>
  	</div>
	</div>
	-->
	
</body>
</html>