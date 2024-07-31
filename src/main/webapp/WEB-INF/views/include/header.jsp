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
        <%@ include file="head.jsp" %>
    </head>
    <body>
        <div class="container position-sticky z-index-sticky top-0">
            <div class="row">
                <div class="col-12">
                    <nav class="navbar navbar-expand-lg blur border-radius-xl top-0 z-index-fixed shadow position-absolute my-3 py-2 start-0 end-0 mx-4">
                        <div class="container-fluid px-0">
                            <a class="navbar-brand font-weight-bolder ms-sm-3" href="#">
                                Serendipity
                            </a>
                            <button class="navbar-toggler shadow-none ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#navigation" aria-controls="navigation-index" aria-expanded="false"
                                    aria-label="Toggle navigation">
                                <span class="navbar-toggler-icon mt-2">
                                    <span class="navbar-toggler-bar bar1"></span>
                                    <span class="navbar-toggler-bar bar2"></span>
                                    <span class="navbar-toggler-bar bar3"></span>
                                </span>
                            </button>
                            <div class="collapse navbar-collapse pt-3 pb-2 py-lg-0 w-100" id="navigation">
                                <ul class="navbar-nav navbar-nav-hover ms-auto">
                                    <li class="nav-item">
                                        <a class="nav-link" href="/">
                                            <i class="material-icons">home</i>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/auth/signup">
                                            <i class="material-icons">회원가입</i>
                                        </a>
                                    </li>
                                    <c:if test="${!isLoginUser}">
                                        <li class="nav-item">
                                            <a class="nav-link" href="/auth/login">
                                                <i class="material-icons">login</i>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${isLoginUser}">
                                        <li class="nav-item">
                                            <a class="nav-link" href="/auth/my-page">
                                                <i class="material-icons">myPage</i>
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" href="#" id="logoutBtn">
                                                <i class="material-icons">logout</i>
                                            </a>
                                        </li>
                                    </c:if>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/community">
                                            <i class="material-icons">board</i>
                                        </a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="/community/gallery">
                                            <i class="material-icons">galleryBoard</i>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
        <script>
            $(function () {
                $('#logoutBtn').click(function () {
                    const form = $('<form>', {
                        'method': 'POST',
                        'action': '/auth/logout'
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
