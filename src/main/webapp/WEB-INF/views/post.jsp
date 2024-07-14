<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="data:,">
    <script src="<c:url value='https://code.jquery.com/jquery-3.7.1.min.js'/>"></script>
    <title>게시글</title>
</head>
<body>
<h1>게시판 작성</h1>

<c:set var="post" value="${postDetailResDTO}"/>
<h2>제목 : ${post.title}</h2>
<h4>작성자 : ${post.author}</h4>
<h4>조회수 : ${post.views}</h4>
<h3>본문</h3>
<p>
    ${post.content}
</p>
생성일 : <fmt:formatDate value="${post.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/> <br>
최종 수정일 : <fmt:formatDate value="${post.updatedDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
<button onclick=window.location.href="/community">게시판으로 돌아가기</button>
<button onclick=window.location.href="/">홈으로 돌아가기</button>

<c:if test="${post.hasImage eq 'Y' }">
    <div id="postImages"></div>
    <script type="text/javascript">
        const postId = "${post.postId}";
    </script>
    <script src="<c:url value='../../resource/js/getImages.js'/>"></script>
</c:if>

</body>
</html>