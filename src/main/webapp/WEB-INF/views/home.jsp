<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 현재 사용자가 로그인 사용자인지 확인
    boolean isLoginUser = SecurityUtil.isLoginUser();
    pageContext.setAttribute("isLoginUser", isLoginUser);
%>
<!doctype html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
    </head>
    <body>
        <h1>홈페이지</h1>

        <button onclick="location.href='/auth/signup'">회원가입</button>

        <c:if test="${!isLoginUser}">
            <button onclick="location.href='/auth/login'">로그인</button>
        </c:if>

        <c:if test="${isLoginUser}">
            <button onclick="location.href='/auth/my-page'">마이 페이지</button>
            <button id="logoutBtn">로그아웃</button>
        </c:if>

        <button onclick="location.href='/community'">게시판</button>
        <button onclick="location.href='/community/gallery'">이미지 게시판</button>

        <script>
            $(function () {
                $('#logoutBtn').click(function () {
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
    </body>
</html>
