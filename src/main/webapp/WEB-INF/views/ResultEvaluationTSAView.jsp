<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="./include/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>수술 경과 상세</title>
	  <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
	  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
 
	<div class="container">  
	  <h2>수술 결과 평가</h2>
	  <p>조금 우수</p>            
	  <table class="table table-striped">
	    <thead>
	      <tr>
	      	<th></th>
	        <th>수술 타입</th>
	        <th>총 점수</th>
	        <th>수술 날짜</th>
	        <th>중간점수1</th>
	        <th>중간점수2</th>
	        <th>중간점수3</th>
	        <th>작성자</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr>
	      	<td></td>
	      	<td>${map.etsa.content_type}</td>
	        <td>${map.etsa.total_score}</td>
	        <td>${map.etsa.upload_date}</td>
	        <td>${map.etsa.middle_score1}</td>
	        <td>${map.etsa.middle_score2}</td>
	        <td>${map.etsa.middle_score3}</td>
	        <td>${map.etsa.username}</td>
	      </tr>
	      <tr>
	      	<td></td>
	        <td>${map.etsa.content_type}</td>
	        <td>${map.etsa.total_score}</td>
	        <td>${map.etsa.upload_date}</td>
	        <td>${map.etsa.middle_score1}</td>
	        <td>${map.etsa.middle_score2}</td>
	        <td>${map.etsa.middle_score3}</td>
	        <td>${map.etsa.username}</td>
	      </tr>
	      <tr>
	      	<td></td>
	        <td>${map.etsa.content_type}</td>
	        <td>${map.etsa.total_score}</td>
	        <td>${map.etsa.upload_date}</td>
	        <td>${map.etsa.middle_score1}</td>
	        <td>${map.etsa.middle_score2}</td>
	        <td>${map.etsa.middle_score3}</td>
	        <td>${map.etsa.username}</td>
	      </tr>
	      <tr>
	      	<td></td>
	        <td>${map.etsa.content_type}</td>
	        <td>${map.etsa.total_score}</td>
	        <td>${map.etsa.upload_date}</td>
	        <td>${map.etsa.middle_score1}</td>
	        <td>${map.etsa.middle_score2}</td>
	        <td>${map.etsa.middle_score3}</td>
	        <td>${map.etsa.username}</td>
	      </tr>
	      <tr>
	      	<td></td>
	        <td>${map.etsa.content_type}</td>
	        <td>${map.etsa.total_score}</td>
	        <td>${map.etsa.upload_date}</td>
	        <td>${map.etsa.middle_score1}</td>
	        <td>${map.etsa.middle_score2}</td>
	        <td>${map.etsa.middle_score3}</td>
	        <td>${map.etsa.username}</td>
	      </tr>
	    </tbody> 
	  </table>	
	</div>
	
	<div class="container">
		<p> 총점수 </p>
		<div class="progress" style="height:30px;">
			<c:choose>
				<c:when test="${map.total_score_status eq 'success'}"> 
					<div class="progress-bar progress-bar-danger progress-bar" role="progressbar" style="width:34%">실패</div>
		  			<div class="progress-bar progress-bar-warning progress-bar" role="progressbar" style="width:33%">경고</div>
		  			<div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" style="width:33%">성공</div>		
				</c:when>
				<c:when test="${map.total_score_status eq 'warning'}">
					<div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" style="width:34%">실패</div>
		  			<div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" style="width:33%">경고</div>
		  			<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" style="width:33%">성공</div>	
				</c:when>
				<c:otherwise>
					<div class="progress-bar progress-bar-danger progress-bar-striped active" role="progressbar" style="width:34%">실패</div>
		  			<div class="progress-bar progress-bar-warning progress-bar-striped" role="progressbar" style="width:33%">경고</div>
		  			<div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" style="width:33%">성공</div>	
				</c:otherwise>
			</c:choose>
		  <div style="width: 12px; height: 30px; position: absolute; background: black; left: ${map.total_score_percent}%;" title="position"></div>
		 </div>
	</div>
	
	<div class="container">
		<p> 중간점수1 </p>
		<div class="progress" style="height:30px;">
		  <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" style="width:34%">실패</div>
		  <div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" style="width:33%">경고</div>
		  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" style="width:33%">성공</div>
		  <div style="width: 12px; height: 30px; position: absolute; background: black; right: 40%;" title="position"></div>
		  </div>
	</div>
	 
	<div class="container">
		<p> 중간점수2 </p>
		<div class="progress" style="height:30px;">
		  <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" style="width:34%">실패</div>
		  <div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" style="width:33%">경고</div>
		  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" style="width:33%">성공</div>
		  <div style="width: 12px; height: 30px; position: absolute; background: black; right: 40%;" title="postion"></div>
		  </div>
	</div>
	
	<div class="container">
		<p> 중간점수3 </p>
		<div class="progress" style="height:30px;">
		  <div class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" style="width:34%">실패</div>
		  <div class="progress-bar progress-bar-warning progress-bar-striped active" role="progressbar" style="width:33%">경고</div>
		  <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" style="width:33%">성공</div>
		  <div style="width: 12px; height: 30px; position: absolute; background: black; right: 40%;" title="postion"></div>
		  </div>
	</div>
	
</body>
</html>