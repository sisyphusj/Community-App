<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">

    <title>회원가입 페이지</title>
</head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<body>
<h1>회원가입</h1>
<form action="${pageContext.request.contextPath}/user/register" method="post">
    <sec:csrfInput/>

    <label for="username">아이디 : </label>
    <input type="text" id="username" name="username" required> <br> <br>

    <label for="password">비밀번호 : </label>
    <input type="text" id="password" name="password" required> <br> <br>

    <label for="name">이름 : </label>
    <input type="text" id="name" name="name" required> <br> <br>

    <input type="submit" value="회원가입">
</form>
</body>
</html>