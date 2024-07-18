<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <script src="<c:url value='https://code.jquery.com/jquery-3.7.1.min.js'/>"></script>
    <title>게시글</title>
</head>
<body>
<c:set var="post" value="${postDetailResDTO}"/>
<h1>게시판 작성</h1>
<h2>제목 : ${post.title}</h2>
<h4>작성자 : ${post.author}</h4>
<h4>조회수 : ${post.views}</h4>
<h3>본문</h3>
<p>
    ${post.content}
</p>
생성일 : <fmt:formatDate value="${post.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/> <br>
최종 수정일 : <fmt:formatDate value="${post.updatedDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
<button onclick=window.location.href="/community">목록으로 돌아가기</button>
<button onclick=window.location.href="/">홈으로 돌아가기</button>

<c:if test="${post.hasImage eq 'Y' }">
    <c:forEach var="file" items="${ImageDetailsResDTOList}">
        <img src="${file.imagePath}" alt="${file.storedName}"/>
    </c:forEach>
</c:if>

<%
    Long currentUserId = null;
    if (SecurityUtil.isLoginUser()) {
        currentUserId = SecurityUtil.getLoginUserId();
    }
    pageContext.setAttribute("currentUserId", currentUserId);
%>

<c:if test="${post.userId eq currentUserId}">
    <button onclick=window.location.href=`/community/posts/${post.postId}/edit`>수정</button>
</c:if>

</body>
</html>