<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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

</head>

<body>
	
	<div class="container">
		<c:forEach var="row" items="${list}">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<th>전문가 평가</th>
					</tr>
					<tr>
						<th>점수: </th>
						<td>${row.score}</td>
					</tr>
					<tr>
						<th>총평: </th>
						<td>${row.replytext}</td>
					</tr>
					<tr>
						<th>평가자: </th>
						<td>${row.replyer}(<fmt:formatDate value="${row.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />)</td>
					</tr>
					<tr>
						<th>평가파일: </th>
						<td>${row.fileName}
						<form name="form-inline" action="/CSP/download" method="get">
						<input type="submit" value="다운로드" class="btn btn-info pull-right" />
						<input type="hidden" name="filepath" value="${row.fileName}" />
						</form>
						</td>
					</tr>
					<tr>
						<th>평가영상: </th>
						<td> 
							<video width="640" height="480" controls autoplay="autoplay">
								<source src="${pageContext.request.contextPath}/assets${row.fileName}" type="video/mp4">
							</video>
						</td>
					</tr>
				</tbody>
			</table>
		</c:forEach>
	</div>
	
</body>

</html>