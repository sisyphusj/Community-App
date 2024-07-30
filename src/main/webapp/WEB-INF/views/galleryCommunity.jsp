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

        <div>
            <%-- 로그인 사용자만 게시글 쓰기 허용 --%>
            <c:if test="${isLoginUser}">
                <button onclick="location.href='/community/gallery/new'">갤러리 게시글 쓰기</button>
            </c:if>

            <%-- 페이지 정렬, 검색 --%>
            <form id="textSearch" action="/community/gallery" method="get">
                <%-- 정렬, 검색 공통 폼 내용 --%>
                <%@ include file="include/searchFormContent.jsp" %>
            </form>

            <h1>갤러리 게시판</h1>

            <table id="postsTable" border="1">
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>썸네일</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                        <th>좋아요</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${posts}">
                        <%-- 썸네일 이미지가 존재하면 렌더링 --%>
                        <c:if test="${post.thumbnail != null}">
                            <tr>
                                <td>${post.postId}</td>
                                <td>
                                    <form action="/community/GALLERY/posts/${post.postId}" method="get" style="display:inline;">
                                        <sec:csrfInput/>
                                        <button type="submit" style="background:none; border:none; color:blue; text-decoration:underline; cursor:pointer; padding:0;">
                                                ${post.title}
                                        </button>
                                        <input type="hidden" name="page" value="${current.page}"/>
                                        <input type="hidden" name="sort" value="${current.sort}"/>
                                        <input type="hidden" name="keywordType" value="${current.keywordType}"/>
                                        <input type="hidden" name="keyword" value="${current.keyword}"/>
                                        <input type="hidden" name="row" value="${current.row}"/>
                                    </form>
                                </td>
                                <td>
                                        <%-- 썸네일 이미지--%>
                                    <img class="thumbnail" src="${post.thumbnail.imagePath}" alt="${post.thumbnail.originName}"/>
                                </td>
                                <td>${post.name}</td>
                                <td>${post.views}</td>
                                <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${post.likes}</td>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>

            <%-- 공통 페이지 네이션 --%>
            <%@ include file="include/pagination.jsp" %>
        </div>

        <button onclick="location.href='/'">메인으로 돌아가기</button>

        <style>
            .pagination {
                display: flex;
                list-style: none;
                padding: 0;
            }

            .pagination li {
                margin: 0 5px;
            }

            .pagination li a {
                text-decoration: none;
                padding: 5px 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                color: #333;
            }

            .pagination li a:hover {
                background-color: #f0f0f0;
            }

            .pagination li span {
                padding: 5px 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                background-color: #e0e0e0;
                color: #333;
            }

            .thumbnail {
                width: 100px;
                height: 100px;
                object-fit: cover;
            }
        </style>
    </body>
</html>
