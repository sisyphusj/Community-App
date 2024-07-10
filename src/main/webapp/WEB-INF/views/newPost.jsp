<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="data:,">
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

    <input type="submit" value="제출">
</form>
</body>
</html>
