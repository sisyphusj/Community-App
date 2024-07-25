<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>게시판</title>
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
        </style>
    </head>
    <body>
        <%
            boolean isLoginUser = SecurityUtil.isLoginUser();
            pageContext.setAttribute("isLoginUser", isLoginUser);
        %>
        <div>
            <c:if test="${isLoginUser}">
                <button onclick="location.href='/community/new'">게시글 쓰기</button>
            </c:if>

            <form id="textSearch" action="/community" method="get">
                <label for="sort">정렬</label>
                <select name="sort" id="sort">
                    <option value="DATE" <c:if test="${param.sort eq 'DATE'}">selected</c:if>>최신순</option>
                    <option value="VIEWS" <c:if test="${param.sort eq 'VIEWS'}">selected</c:if>>조회순</option>
                </select>

                <label for="keyword">검색하기</label>
                <input type="text" id="keyword" name="keyword" value="${param.keyword}"/>

                <input type="hidden" name="page" value="1"/>

                <label for="row">게시글 개수</label>
                <select name="row" id="row">
                    <option value=10 <c:if test="${param.row eq 10}">selected</c:if>>10개씩</option>
                    <option value=30 <c:if test="${param.row eq 30}">selected</c:if>>30개씩</option>
                    <option value=50 <c:if test="${param.row eq 50}">selected</c:if>>50개씩</option>
                    <option value=100 <c:if test="${param.row eq 100}">selected</c:if>>100개씩</option>
                </select>
                <input type="submit" value="검색">
            </form>

            <h1>게시판</h1>

            <table id="postsTable" border="1">
                <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>조회수</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="post" items="${pageResDTO.postSummaryResDTO}">
                        <tr>
                            <td>${post.postId}</td>
                            <td><a href="/community/posts/${post.postId}">${post.title}</a></td>
                            <td>${post.name}</td>
                            <td>${post.views}</td>
                            <td><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <%-- 현재 페이지 --%>
            <c:set var="page" value="${pageResDTO.currentPage}"/>
            <nav>
                <ul class="pagination">
                    <%-- 첫 페이지 링크 --%>
                    <li>
                        <a href="/community?page=1&sort=${param.sort}&keyword=${param.keyword}&row=${param.row}">&laquo;</a>
                    </li>

                    <%-- 이전 페이지 링크 : 현재 페이지가 2페이지 이상일 때 --%>
                    <c:if test="${page > 1}">
                        <li>
                            <a href="/community?page=${page - 1}&sort=${param.sort}&keyword=${param.keyword}&row=${param.row}">&lt;</a>
                        </li>
                    </c:if>

                    <%-- 페이지 번호 링크 : 현재 페이지 기준 렌더링되는 첫 페이지 번호, 마지막 페이지 번호 --%>
                    <c:forEach begin="${pageResDTO.firstPage}" end="${pageResDTO.lastPage}" var="i">
                        <li>
                            <a href="/community?page=${i}&sort=${param.sort}&keyword=${param.keyword}&row=${param.row}">${i}</a>
                        </li>
                    </c:forEach>

                    <%-- 다음 페이지 링크 : 현재 페이지가 끝 페이지가 아닐 때 --%>
                    <c:if test="${page < pageResDTO.totalPageCount}">
                        <li>
                            <a href="/community?page=${page + 1}&sort=${param.sort}&keyword=${param.keyword}&row=${param.row}">&gt;</a>
                        </li>
                    </c:if>

                    <%-- 끝 페이지 링크 --%>
                    <li>
                        <a href="/community?page=${pageResDTO.totalPageCount}&sort=${param.sort}&keyword=${param.keyword}&row=${param.row}">&raquo;</a>
                    </li>
                </ul>
            </nav>
        </div>
        <button onclick="location.href='/'">메인으로 돌아가기</button>
    </body>
</html>
