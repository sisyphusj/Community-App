<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    boolean isLoginUser = SecurityUtil.isLoginUser();
    if (isLoginUser) {
        response.sendRedirect("/");
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <link rel="icon" href="data:,">
    <title>로그인</title>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(() => {
            $('form').on('submit', () => {
                const username = $("#username").val();
                const password = $("#password").val();

                if (!username) {
                    alert("아이디를 입력하세요.");
                    return false;
                }

                if (!password) {
                    alert("비밀번호를 입력하세요.");
                    return false;
                }

                return true;
            });
        });
    </script>
</head>
<body>

<h1>로그인</h1>
<form action="${pageContext.request.contextPath}/auth/signin" method="post">
    <sec:csrfInput/>

    <label for="username">아이디 : </label>
    <input type="text" id="username" name="username"> <br> <br>

    <label for="password">비밀번호 : </label>
    <input type="password" id="password" name="password"> <br> <br>

    <input type="submit" value="로그인">
</form>

<button type="button" onclick="location.href='${pageContext.request.contextPath}/oauth2/authorization/google'">구글 로그인</button>
<button type="button" onclick="location.href='${pageContext.request.contextPath}/oauth2/authorization/naver'">네이버 로그인</button>
<button type="button" onclick="location.href='${pageContext.request.contextPath}/oauth2/authorization/kakao'">카카오 로그인</button>
</body>
</html>
