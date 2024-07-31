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
        <c:set var="baseUrl" value="/community/gallery"/>

        <%-- 현재 페이지네이션 정보 --%>
        <c:set var="page" value="${pageResDTO}"/>

        <%-- 현재 페이지 게시글 목록 정보 --%>
        <c:set var="posts" value="${pageResDTO.postSummaryResDTO}"/>

        <div class="container mt-5">
            <%-- 로그인 사용자만 게시글 쓰기 허용 --%>
            <c:if test="${isLoginUser}">
                <button class="btn btn-primary mb-3" onclick="location.href='/community/gallery/new'">갤러리 게시글 쓰기</button>
            </c:if>

            <%-- 페이지 정렬, 검색 --%>
            <form id="textSearch" action="/community/gallery" method="get" class="mb-3">
                <%-- 정렬, 검색 공통 폼 내용 --%>
                <%@ include file="include/searchFormContent.jsp" %>
            </form>

            <h2>여행지 갤리러 게시판</h2>

            <button class="btn btn-secondary mt-3" onclick="location.href='/'">메인으로 돌아가기</button>

            <%-- 게시글 목록 --%>
            <div class="row">
                <c:forEach var="post" items="${posts}">
                    <%-- 썸네일 이미지가 존재하면 렌더링 --%>
                    <c:if test="${post.thumbnail != null}">
                        <div class="col-md-4 mb-4">
                            <div class="card">
                                <img class="card-img-top" src="${post.thumbnail.imagePath}" alt="${post.thumbnail.originName}" style="height: 200px; object-fit: cover;">
                                <div class="card-body">
                                    <h5 class="card-title">
                                        <form action="/community/GALLERY/posts/${post.postId}" method="get" style="display:inline;">
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
                                    </h5>
                                    <p class="card-text">${post.name}</p>
                                    <p class="card-text">${post.category}</p>
                                    <p class="card-text"><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                                    <p class="card-text">조회수: ${post.views} 좋아요: ${post.likes}</p>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <%-- 공통 페이지 네이션 --%>
            <%@ include file="include/pagination.jsp" %>
        </div>
    </body>
</html>
