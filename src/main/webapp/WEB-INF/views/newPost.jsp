<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="data:,">
    <script src="<c:url value='https://code.jquery.com/jquery-3.7.1.min.js'/>"></script>
    <script src="<c:url value='../../resource/js/imageUpload.js'/>"></script>
    <script src="<c:url value='../../resource/js/postSubmit.js'/>"></script>
    <title>게시판 작성</title>
</head>
<body>
<h1>게시판 작성</h1>
<form action="/community/add" method="post">
    <sec:csrfInput/>
    <label for="title">제목</label><br>
    <input type="text" id="title" name="title" required><br><br>

    <label for="content">본문</label><br>
    <textarea id="content" name="content" rows="10" required></textarea><br><br>

    <button type="button" id="postSubmitBtn">등록</button>
</form>
<form id="uploadForm" action="/image/upload" method="post" enctype="multipart/form-data">
    <sec:csrfInput/>
    <label for="imageFiles">이미지 첨부파일</label><br>
    <input type="file" id="imageFiles" name="uploadFiles" multiple>
</form>
<h2>업로드</h2>
<ul id="imageList"></ul>
</body>
</html>
