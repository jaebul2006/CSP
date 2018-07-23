<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Ajax</title>
<%@ include file="./include/header.jsp" %>
<style>
	.fileDrop {
		width: 600px;
		height: 200px;
		border: 1px dotted blue;
	}
	
	small {
		margin-left: 3px;
		font-weight: bold;
		color: gray;
	}
</style>

	<script>
		$(document).ready(function(){
			$(".fileDrop").on("dragenter dragover", function(event){
				event.preventDefault();
			});
		});
		
		$(".fileDrop").on("drop", function(event){
			event.preventDefault();
			var files = event.originalEvent.dataTransfer.files;
			var file = files[0];
			console.log(file);
			var formData = new FormData();
			formData.append("file", file);
			
			var ajaxReq = $.ajax({
				type: "post",
				url: "${path}/upload/uploadAjax",
				data: formData,
				//dataType: "text",
				processData: false,
				contentType: false,
				/*success: function(data){
					console.log(data);
					var str = "";
					if(checkImageType(data)){
						str = "<div><a href='${path}/upload/displayFile?fileName="+getImageLink(data)+"'>";
						str += "<img src='${path}/upload/displayFile?fileName="+data"'></a>";
					}
					else{
						str = "<div><a href='${path}/upload/displayFile?fileName="+getImageLink(data)+"'>";
					}
					str += "<span data-src="+data+">[삭제]</span></div>";
					$(".uploadedList").append(str);
				},
				error :	function(request, status, error){
					console.log(request.responseText);
				}*/
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
				alert("업로드완료");
				console.log("upload comp");
			});
			
			ajaxReq.fail(function(jqXHR){
				alert("업로드실패");
				console.log("upload fail");
			});
			
		});
		
		$(".uploadedList").on("click", "span", function(event){
			alert("이미지 삭제");
			var that = $(this);
			$.ajax({
				url: "${path}/upload/deleteFile",
				type: "post",
				data: {fileName:$(this).attr("data-src")},
				dataType: "text",
				success: function(result){
					if(result == "deleted"){
						that.parent("div").remove();
					}
				},
				error :	function(request, status, error){
					console.log(request.responseText);
				}
			});
		});
		
		function getOriginalName(fileName)
		{
			if(!checkImageType(fileName)){
				return;
			}
			var front = fileName.substr(0, 12);
			var end = filename.substr(14);
			console.log(front);
			console.log(end);
			return front + end;
		}
		
		function checkImageType(fileName)
		{
			var pattern = /jpg|gif|png|jpeg/i;
			return fileName.match(pattern);
		}
		
	</script>
</head>
<body>
	<h2>AJAX File Upload</h2>
	<div class="fileDrop" value="이곳에 파일을 드래그 해주세요"></div>
	<div class="uploadedList"></div>
	
	<div id="uploadProgress"></div>
</body>
</html>