<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>로그인</title>

    <script>
        function validateForm() {
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            if (username === "") {
                alert("아이디를 입력하세요.");
                return false;
            }

            if (password === "") {
                alert("비밀번호를 입력하세요.");
                return false;
            }

            return true;
        }
    </script>
</head>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<body>
<h1>로그인</h1>
<form action="${pageContext.request.contextPath}/auth/signin" method="post" onsubmit="return validateForm()">
    <sec:csrfInput/>

    <label for="username">아이디 : </label>
    <input type="text" id="username" name="username"> <br> <br>

    <label for="password">비밀번호 : </label>
    <input type="password" id="password" name="password"> <br> <br>

    <input type="submit" value="로그인">
</form>
</body>
</html>
