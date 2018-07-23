<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
</head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>업로드</title>

<script>
	$(document).ready(function(){
		
		$("#btnList").click(function(){
			location.href = "${path}/CoachingBoard/CoachingBoardListHome";
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
							$('#uploadProgress').text(perc + '%');
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
				});
				
				ajaxReq.fail(function(jqXHR){
					alert("업로드실패");
				});	
			}
		});
		
		$("#btnSave").click(function(){
			var title = $("#title").val();
			var content = $("#content").val();
			
			var writer = $("#writer").val();
			console.log("작성자" + writer);
			
			if(title == ""){
				alert("제목을 입력하세요");
				document.form1.title.focus();
				return;
			}
			if(content == ""){
				alert("내용을 입력하세요");
				document.form1.content.focus();
				return;
			}
	
			// 첨부파일 이름을 form에 추가
			var that = $("#form1");
			var str = "<input type='hidden' name='fileName' value='"+$('.file').val()+"'>";
			
			$("#form1").append(str);
			// 폼에 입력한 데이터를 서버로 전송
			document.form1.submit();
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
			/*if(checkImageType(fullName)){
				imgsrc = "/CSP/upload/displayFile?fileName=" + fullName;
				console.log(imgsrc);
				fileLink = fullName.substr(14);
				console.log(fileLink);
				var front = fullName.substr(0, 12);
				console.log(front);
				var end = fullName.substr(14);
				console.log(end);
				getLink = "/CSP/upload/displayFile?fileName=" + front + end;
				console.log(getLink);
			}
			else{
				fileLink = fullName.substr(12);
				console.log(fileLink);
				getLink = "/CSP/upload/displayFile?fileName=" + fullName;
				console.log(getLink);
			}*/
			fileName = fileLink.substr(fileLink.indexOf("_")+1);
			console.log(fileName);
			return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
		}
		
	});
</script>

<style>
	.fileDrop{
		width: 100%;
		height: 100px;
		border: 2px dotted gray;
	}
</style>

</head>

<!-- <body style="background-image:url(../assets/CoachingList/jdhold.png)"; background-repeat:no-repeat> -->
<body>
	<div id="header">
		<div id="top-header">
			<div id="company-logo">
				<h1><img src="../static/site/images/logo-1.png" alt="BIZZ GROUP" id="logo" /></h1>
			</div>
			<ul id="main-navigation">
				<li><a href="/CSP">홈</a></li>
				<li><a href="#">제품소개</a></li>
				<li class="active"><a href="/CSP/CoachingBoard/CoachingBoardList?id=${pageContext.request.remoteUser}">코칭 대시보드</a></li>
				<li><a href="#">커뮤니티</a></li>
				<li><a href="#">제품문의</a></li>
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
	<!--  <form name="form1" id="form1" method="post" action="${path}/CoachingBoard/insert">
		<div>제목
			<input name="title" id="title" size="80" placeholder="제목을 입력해주세요">
		</div>
		<div>
			내용
			<textarea name="content" id="content" rows="4" cols="80" placeholder="내용을 입력해주세요"></textarea>
		</div>
		
		<div>
			<div class="fileDrop">이곳에 파일을 드래그 해주세요</div>
			<div id="uploadedList"></div>
		</div>
		<div id="uploadProgress"></div>
		
		<button type="button" id="btnSave">확인</button>
		<button type="reset">취소</button>
		<input type='hidden' name="${_csrf.parameterName}" value="${_csrf.token}" ></input>
	</form>
	-->
	
	<div class="container">
		<table class="table table-bordered">
			<!--  <thead>
				<caption>글쓰기</caption>
			</thead> -->
			<tbody>
				<form name="form1" id="form1" method="post" action="${path}/CoachingBoard/insert">
					<tr>
						<th>제목: </th>
						<td><input type="text" placeholder="제목을 입력하세요" name="title" id="title" class="form-control"/></td>
					</tr>
					<tr>
						<th>내용: </th>
						<td><textarea cols="10" placeholder="내용을 입력하세요" name="content" id="content" class="form-control"></textarea></td>
					</tr>
					<tr>
						<th>첨부파일: </th>
						<td><textarea cols="10" placeholder="이곳에 파일을 드래그 해주세요" name="content" class="fileDrop" id="uploadedList"></textarea></td>
					</tr>
					<tr>
						<th>작성자: </th>
						<td><input type="text" readonly placeholder="" name="writer" id="writer" value="${pageContext.request.remoteUser}" class="form-control"/></td>
					</tr>
					<tr>
						<th>수술 타입:</th>
						<td><input type="text" readonly placeholder="" name="content_type" id="content_type" value="${map.content_type}" class="form-control"/></td>
					</tr>
					<tr>
		                <td colspan="2">
		                    <button type="button" id="btnSave" class="btn btn-info pull-right">등록</button>
		                    <button type="button" id="btnList" class="btn btn-primary pull-left">리스트</button>
		                </td>
           			</tr>
				</form>
			</tbody>
		</table>
	</div>
	<div id="uploadProgress"></div>
	
</body>
</html>