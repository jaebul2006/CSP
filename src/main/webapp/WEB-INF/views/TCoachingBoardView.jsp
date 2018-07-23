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
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>게시글</title>
	
	<style>
	.fileDrop {
		width: 100%;;
		height: 50px;
		border: 1px dotted blue;
	}
	
	small {
		margin-left: 3px;
		font-weight: bold;
		color: gray;
	}
</style>
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
				<li class="active"><a href="/CSP/CoachingBoard/TCoachingBoardList">코칭 대시보드</a></li>
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
	
	<div id="dash_line" style="border:none; border:5px double orange;"></div>
	
	<div id="listReply"></div>
	
	<div id="dash_line2" style="border:none; border:5px double orange;"></div>
	
	<div class="container">
		<table class="table table-bordered">
			<tbody>
				<form name="eval_form" id="eval_form" method="post" action="${path}/CoachingBoardReply/insert">
					<tr>
						<th>게시넘버: </th>
						<td><input type="text" style="width:100%;" id="bno" class="form-control" value="${dto.bno}" readonly/></td>
					</tr>
					<tr>
						<th>점수: </th>
						<td><input type="text" style="width:100%;" id="score" class="form-control"/></td>
					</tr>
					<tr>
						<th>총평: </th>
						<td><textarea rows="10" style="width:100%;" id="replytext" class="form-control"></textarea></td>
					</tr>
					<tr>
						<th>파일: </th>
						<td><textarea cols="10" placeholder="이곳에 파일을 드래그 해주세요" name="content" class="fileDrop" id="uploadedList"></textarea></td>
					</tr>
					<tr>
						<th>작성자: </th>
						<td><input type="text" id="writer" class="form-control" value="${dto.writer}" readonly /></td>
					</tr>
					<tr>
						<th>평가자: </th>
						<td><input type="text" style="width:100%;" id="replyer" class="form-control" value="${pageContext.request.remoteUser}" readonly /></td>
					</tr>
					<tr>
		                <td colspan="2">
		                 	<button type="button" id="BtnEval" class="btn btn-primary">평가등록</button>
		                </td>
           			</tr>
				</form>
			</tbody>
		</table>
	</div>    
	
	
	
	<script>
	$(document).ready(function(){
		
		var fileName;
		
		listReplyRest("1");	
		
		$("#btnList").click(function(){
			location.href="${path}/CoachingBoard/TCoachingBoardList?curPage=${curPage}&searchOption=${searchOption}&keyword=${keyword}";
		});
		
		$("#BtnEval").click(function(){
			var bno = $("#bno").val();
			var score = $("#score").val();
			var replytext = $("#replytext").val();
			var writer = $("#writer").val();
			var replyer = $("#replyer").val();
			
			var param = {"bno":bno, "score":score, "replytext":replytext, "writer": writer, 
					"replyer": replyer, "fileName":fileName, "content_type":${dto.content}};
			
			$.ajax({
				type: "post",
				url: "/CSP/insertReply",
				data: param,
				success: function(result){
					//console.log(result);
					location.href="${path}/CoachingBoard/TCoachingBoardList?curPage=${curPage}&searchOption=${searchOption}&keyword=${keyword}";
				},
				error :	function(request, status, error){
					console.log(request.responseText);
				}
			});
		});
		
		$(".fileDrop").on("dragenter dragover", function(e){
			e.preventDefault();
		});
		
		$(".fileDrop").on("drop", function(e){
			e.preventDefault();
			var files = e.originalEvent.dataTransfer.files;
			var file = files[0];
			var formData = new FormData();
			formData.append("file", file);
			
			if(checkMovieType(file.name) == null){
				alert("실습 영상 파일만 업로드 할 수 있습니다");
				return;
			}	
			else{
				$(".fileDrop").text(file.name);
				
				var ajaxReq = $.ajax({
					url: "${path}/upload/uploadAjax",
					type: "post",
					data: formData,
					processData: false,
					contentType: false,
					xhr: function(){
						var xhr = $.ajaxSettings.xhr();
						xhr.upload.onprogress = function(event){
							var perc = Math.round((event.loaded / event.total) * 100);
							console.log(perc);
							//$('#uploadProgress').text(perc + '%');
						};
						return xhr;
					},
					beforeSend: function(xhr){
						console.log(xhr);
					}
				});

				ajaxReq.done(function(msg){
					console.log(msg);
					var fileInfo = getFileInfo(msg);
					console.log(fileInfo.fullName);
					var html ="<a href='"+fileInfo.getLink+"'>"+fileInfo.fileName+"</a><br>";
					html += "<input type='hidden' class='file' value='"+fileInfo.fullName+"'>";
					$("#uploadedList").append(html);
					
					fileName = msg;
				});
				
				ajaxReq.fail(function(jqXHR){
					alert("업로드실패");
				});	
			}
		});

		function checkMovieType(fileName)
		{
			var pattern = /mp4|mpeg|avi/i;
			return fileName.match(pattern);
		}
		
		function getFileInfo(fullName)
		{
			var fileName, imgsrc, getLink, fileLink;
			fileLink = fullName.substr(12);
			console.log(fileLink);
			getLink = "/CSP/upload/displayFile?fileName=" + fullName;
			console.log(getLink);
			fileName = fileLink.substr(fileLink.indexOf("_")+1);
			console.log(fileName);
			return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
		}
		
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