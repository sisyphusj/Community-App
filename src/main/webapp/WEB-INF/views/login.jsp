<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 현재 사용자가 로그인 사용자인지 확인
    boolean isLoginUser = SecurityUtil.isLoginUser();
    if (isLoginUser) {
        response.sendRedirect("/");
    }
%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
    </head>
    <body>
        <div class="container mt-5">
            <h1>로그인</h1>

            <form action="/auth/signin" method="post" class="needs-validation" novalidate>
                <sec:csrfInput/>

                <div class="form-group">
                    <label for="username">아이디</label>
                    <input type="text" class="form-control" id="username" name="username" required>
                    <div class="invalid-feedback">
                        아이디를 입력하세요.
                    </div>
                </div>

                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password" name="password" required>
                    <div class="invalid-feedback">
                        비밀번호를 입력하세요.
                    </div>
                </div>

                <button type="submit" class="btn btn-primary">로그인</button>
            </form>

            <div class="mt-3">
                <button type="button" class="btn btn-outline-info" onclick="location.href='${pageContext.request.contextPath}/oauth2/authorization/google'">구글 로그인</button>
                <button type="button" class="btn btn-outline-success" onclick="location.href='${pageContext.request.contextPath}/oauth2/authorization/naver'">네이버 로그인</button>
                <button type="button" class="btn btn-outline-warning" onclick="location.href='${pageContext.request.contextPath}/oauth2/authorization/kakao'">카카오 로그인</button>
            </div>

            <button class="btn btn-secondary mt-3" onclick="location.href='/'">메인으로 돌아가기</button>
        </div>

        <script>
            $(function () {
                $('form').on('submit', (event) => {
                    if (event.target.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    $(event.target).addClass('was-validated');
                });
            });
        </script>
    </body>
</html>
