<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="icon" href="data:,">

    <title>회원가입 페이지</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('form').on('submit', function () {
                const username = $("#username").val();
                const password = $("#password").val();
                const name = $("#name").val();

                if (!username) {
                    alert("아이디를 입력하세요.");
                    return false;
                }

                if (!password) {
                    alert("비밀번호를 입력하세요.");
                    return false;
                }

                if (!name) {
                    alert("이름을 입력하세요");
                    return false
                }

                return true;
            });
        });
    </script>
</head>
<body>
<h1>회원가입</h1>
<form action="${pageContext.request.contextPath}/auth/register" method="post">
    <sec:csrfInput/>

    <label for="username">아이디 : </label>
    <input type="text" id="username" name="username"> <br> <br>

    <label for="password">비밀번호 : </label>
    <input type="text" id="password" name="password"> <br> <br>

    <label for="name">이름 : </label>
    <input type="text" id="name" name="name"> <br> <br>

    <input type="submit" value="회원가입">
</form>
</body>
</html>