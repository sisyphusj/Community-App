<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%
    // 현재 사용자가 로그인 사용자인지 확인
    boolean isLoginUser = SecurityUtil.isLoginUser();
    pageContext.setAttribute("isLoginUser", isLoginUser);
%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
    </head>
    <body>
        <%-- 현재 페이지 정보(정렬, 검색) --%>
        <c:set var="current" value="${pageReqDTO}"/>

        <%-- 게시판 기본 URL --%>
        <c:set var="baseUrl" value="/community"/>

        <%-- 현재 페이지네이션 정보 --%>
        <c:set var="page" value="${pageResDTO}"/>

        <%-- 현재 페이지 게시글 목록 정보 --%>
        <c:set var="posts" value="${pageResDTO.postSummaryResDTO}"/>

        <div class="container mt-5">
            <%-- 로그인 사용자만 게시글 쓰기 허용 --%>
            <c:if test="${isLoginUser}">
                <button class="btn btn-primary mb-3" onclick="location.href='/community/new'">게시글 쓰기</button>
            </c:if>

            <%-- 페이지 정렬, 검색 --%>
            <form id="textSearch" action="/community" method="get" class="mb-3">
                <%-- 정렬, 검색 공통 폼 내용 --%>
                <%@ include file="include/searchFormContent.jsp" %>
            </form>

            <h2>여행지 게시판</h2>

            <button class="btn btn-secondary mt-3" onclick="location.href='/'">메인으로 돌아가기</button>

            <%-- 게시글 목록 --%>
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>조회수</th>
                            <th>작성일</th>
                            <th>좋아요</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="post" items="${posts}">
                            <tr>
                                <td>${post.postId}</td>
                                <td>
                                    <form action="/community/NORMAL/posts/${post.postId}" method="get" style="display:inline;">
                                        <sec:csrfInput/>
                                        <button type="submit" class="btn btn-link p-0" style="text-decoration:underline; color:blue;">
                                                ${post.title}
                                        </button>
                                        <input type="hidden" name="page" value="${current.page}"/>
                                        <input type="hidden" name="sort" value="${current.sort}"/>
                                        <input type="hidden" name="keywordType" value="${current.keywordType}"/>
                                        <input type="hidden" name="keyword" value="${current.keyword}"/>
                                        <input type="hidden" name="row" value="${current.row}"/>
                                    </form>
                                </td>
                                <td>${post.name}</td>
                                <td>${post.views}</td>
                                <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${post.likes}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- 공통 페이지 네이션 --%>
            <%@ include file="include/pagination.jsp" %>
        </div>
    </body>
</html>
