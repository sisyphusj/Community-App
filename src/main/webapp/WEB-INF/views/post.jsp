<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <script src="<c:url value='https://code.jquery.com/jquery-3.7.1.min.js'/>"></script>
        <title>게시글</title>
    </head>
    <body>
        <c:set var="post" value="${postDetailResDTO}"/>

        <script>
            $(document).ready(() => {
                $('.replyButton').click(function () {
                    const commentDiv = $(this).closest('.commentDiv');
                    const commentId = commentDiv.data('comment-id');

                    // 모든 replyInputContainer 비우기
                    $('.replyInputContainer').empty();

                    // 선택된 replyInputContainer에 답글 입력 폼 추가
                    $(`#replyInputContainer-${'${commentId}'}`).html(`
                        <form action="/comment" method="post">
                            <sec:csrfInput/>
                            <textarea name="content" required></textarea>
                            <input type="hidden" name="parentId" value="${'${commentId}'}">
                            <input type="hidden" name="postId" value="${post.postId}">
                            <button type="submit">등록</button>
                        </form> `);
                });

                $('.editButton').click(function () {
                    const commentDiv = $(this).closest('.commentDiv');
                    const commentId = commentDiv.data('comment-id');

                    // 모든 editInputContainer 비우기
                    $('.editInputContainer').empty();

                    // 선택된 editInputContainer에 답글 입력 폼 추가
                    $(`#editInputContainer-${'${commentId}'}`).html(`
                        <form action="/comment/edit" method="post">
                            <sec:csrfInput/>
                            <textarea name="content" required></textarea>
                            <input type="hidden" name="commentId" value="${'${commentId}'}">
                            <input type="hidden" name="postId" value="${post.postId}">
                            <button type="submit">등록</button>
                        </form> `);
                });
            });

        </script>

        <h1>게시판 작성</h1>
        <h2>제목 : ${post.title}</h2>
        <h4>작성자 : ${post.author}</h4>
        <h4>조회수 : ${post.views}</h4>
        <h3>본문</h3>
        <p>${post.content}</p>
        생성일 : <fmt:formatDate value="${post.createdDate}" pattern="yyyy-MM-dd HH:mm:ss"/> <br>
        최종 수정일 : <fmt:formatDate value="${post.updatedDate}" pattern="yyyy-MM-dd HH:mm:ss"/>

        <button onclick=window.location.href="/community?page=1&sort=DATE">목록으로 돌아가기</button>
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

        <c:if test="${currentUserId != null}">
            <form action="/comment" method="post">
                <sec:csrfInput/>
                <label for="content">댓글</label><br>
                <input type="text" id="content" name="content" required><br><br>

                <input type="hidden" id="postId" name="postId" value="${post.postId}">

                <button type="submit">등록</button>
            </form>
        </c:if>

        <c:forEach var="comment" items="${commentDetailResDTOList}">
            <c:choose>
                <c:when test="${comment.parentId != null}">
                    <div class="commentDiv" data-comment-id="${comment.commentId}">
                        <p class="topLevelComment">
                                ${comment.author} : ${comment.content}
                                ${comment.createdDate}
                        </p>
                        <c:if test="${currentUserId != null}">
                            <button type="button" class="replyButton">답글 쓰기</button>
                        </c:if>
                        <c:if test="${currentUserId == comment.userId}">
                            <button type="button" class="editButton">수정</button>
                            <button onclick=window.location.href=`/comment/${comment.postId}/remove?commentId=${comment.commentId}`>삭제</button>
                        </c:if>
                        <div class="replyInputContainer" id="replyInputContainer-${comment.commentId}"></div>
                        <div class="editInputContainer" id="editInputContainer-${comment.commentId}"></div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="commentDiv" data-comment-id="${comment.commentId}">
                        <p class="comment">
                                ${comment.author} : ${comment.content}
                                ${comment.createdDate}
                        </p>
                        <c:if test="${currentUserId != null}">
                            <button type="button" class="replyButton">답글 쓰기</button>
                        </c:if>
                        <c:if test="${currentUserId == comment.userId}">
                            <button type="button" class="editButton">수정</button>
                            <button onclick=window.location.href=`/comment/${comment.postId}/remove?commentId=${comment.commentId}`>삭제</button>
                        </c:if>
                        <div class="replyInputContainer" id="replyInputContainer-${comment.commentId}"></div>
                        <div class="editInputContainer" id="editInputContainer-${comment.commentId}"></div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${post.userId eq currentUserId}">
            <button onclick=window.location.href=`/community/posts/${post.postId}/edit`>수정</button>
        </c:if>

    </body>
</html>
