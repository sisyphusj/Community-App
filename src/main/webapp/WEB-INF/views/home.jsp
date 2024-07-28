<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <link rel="icon" href="data:,">

        <title>홈페이지</title>

        <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
        <script type="text/javascript">
            $(() => {
                $('#logoutBtn').click(() => {
                    const form = $('<form>', {
                        'method': 'POST',
                        'action': '${pageContext.request.contextPath}/auth/logout'
                    });

                    const csrfInput = $('<input>', {
                        'type': 'hidden',
                        'name': '_csrf',
                        'value': '${_csrf.token}'
                    });

                    form.append(csrfInput);
                    $('body').append(form);
                    form.submit();
                });
            });
        </script>
    </head>
    <body>

        <%
            boolean isLoginUser = SecurityUtil.isLoginUser();
            pageContext.setAttribute("isLoginUser", isLoginUser);
        %>

        <h1>홈페이지</h1>

        <button onclick="location.href='<c:url value='/auth/signup'/>'">회원가입</button>
        <c:if test="${!isLoginUser}">
            <button onclick="location.href='<c:url value='/auth/login'/>'">로그인</button>
        </c:if>
        <c:if test="${isLoginUser}">
            <button onclick="location.href='<c:url value='/auth/my-page'/>'">마이 페이지</button>
        </c:if>
        <button id="logoutBtn">로그아웃</button>
        <button onclick="location.href='<c:url value='/community'/>'">게시판</button>
    </body>
</html>
