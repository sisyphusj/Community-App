<%@ page import="me.sisyphusj.community.app.utils.SecurityUtil" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <script src="<c:url value='https://code.jquery.com/jquery-3.7.1.min.js'/>"></script>
        <title>게시글</title>
    </head>
    <body>
        <c:set var="post" value="${postDetailResDTO}"/>

        <%
            Long currentUserId = null;
            if (SecurityUtil.isLoginUser()) {
                currentUserId = SecurityUtil.getLoginUserId();
            }
            pageContext.setAttribute("currentUserId", currentUserId);
        %>

        <script>
            const removeCommentImage = (commentId, imageId, element) => {
                const csrfToken = $('input[name="_csrf"]').val();
                $.ajax({
                    url: `/images/comment/remove?commentId=${'${commentId}'}&imageId=${'${imageId}'}`,
                    type: "GET",
                    beforeSend: (xhr) => {
                        xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
                    },
                    success: function () {
                        alert("이미지가 제거되었습니다.");
                        $(element).closest('div.image-container').remove();
                    },
                    error: function (error) {
                        console.error(error);
                        alert("이미지 삭제 중 오류가 발생하였습니다.");
                    }
                });
            }

            const handlePostLikeButton = () => {
                const liked = $('#postLikeButton').data('liked');

                if (liked) {
                    $.ajax({
                        url: '/likes/post/dislike',
                        type: 'GET',
                        data: {postId: ${post.postId}},
                        success: (response) => {
                            $('#postLikes').text("좋아요 수 : " + response);
                            $('#postLikeButton').text('좋아요').data('liked', false);
                        },
                        error: (error) => {
                            alert("페이지를 불러오던 도중 문제가 생겼습니다.");
                            window.location.href = "/"
                        }
                    });
                } else {
                    $.ajax({
                        url: '/likes/post',
                        type: 'GET',
                        data: {postId: ${post.postId}},
                        success: (response) => {
                            $('#postLikes').text("좋아요 수 : " + response);
                            $('#postLikeButton').text('좋아요 취소').data('liked', true);
                        },
                        error: (error) => {
                            alert("페이지를 불러오던 도중 문제가 생겼습니다.");
                            window.location.href = "/"
                        }
                    });
                }
            }

            const handleCommentLikeButton = (commentId) => {
                const commentLikeButton = $(`#comment-like-button-${'${commentId}'}`);
                const liked = commentLikeButton.data('liked');


                if (liked) {
                    $.ajax({
                        url: '/likes/comment/dislike',
                        type: 'GET',
                        data: {commentId: commentId},
                        success: (response) => {
                            $(`#comment-likes-${'${commentId}'}`).text("좋아요 개수 : " + response);
                            $(`#comment-like-button-${'${commentId}'}`).text('좋아요').data('liked', false);
                        },
                        error: (error) => {
                            alert("페이지를 불러오던 도중 문제가 생겼습니다.");
                            window.location.href = "/"
                        }
                    });
                } else {
                    $.ajax({
                        url: '/likes/comment',
                        type: 'GET',
                        data: {commentId: commentId},
                        success: (response) => {
                            $(`#comment-likes-${'${commentId}'}`).text("좋아요 개수 : " + response);
                            $(`#comment-like-button-${'${commentId}'}`).text('좋아요 취소').data('liked', true);
                        },
                        error: (error) => {
                            alert("페이지를 불러오던 도중 문제가 생겼습니다.");
                            window.location.href = "/"
                        }
                    });
                }
            }
            $(document).ready(() => {
                const userId = ${currentUserId == null ? 'null' : currentUserId};
                let fileList = [];

                $('.replyButton').click(function () {
                    const commentDiv = $(this).closest('.commentDiv');
                    const commentId = commentDiv.data('comment-id');

                    // 모든 replyInputContainer 비우기
                    $('.replyInputContainer').empty();

                    // 선택된 replyInputContainer에 답글 입력 폼 추가
                    $(`#replyInputContainer-${'${commentId}'}`).html(`
                        <form action="/comment" method="post" enctype="multipart/form-data">
                            <sec:csrfInput/>
                            <textarea name="content" required></textarea>
                            <input type="hidden" name="parentId" value="${'${commentId}'}">
                             <label for="imageFiles">이미지 첨부파일</label><br>
                            <input type="file" id="imageFiles" name="images" multiple>
                            <ul id="imageList" class="imageList"></ul>
                            <input type="hidden" name="postId" value="${post.postId}">
                            <button type="submit">등록</button>
                        </form> `);
                });

                $('.editButton').click(function () {
                    const commentDiv = $(this).closest('.commentDiv');
                    const commentId = commentDiv.data('comment-id');
                    const content = commentDiv.data('comment-content');

                    // 모든 editInputContainer 비우기
                    $('.editInputContainer').empty();

                    // 선택된 editInputContainer에 답글 입력 폼 추가
                    $(`#editInputContainer-${'${commentId}'}`).html(`
                        <form action="/comment/edit" method="post" enctype="multipart/form-data">
                            <sec:csrfInput/>
                            <textarea name="content" required>${'${content}'}</textarea>
                            <input type="hidden" name="commentId" value="${'${commentId}'}">
                            <label for="imageFiles">이미지 첨부파일</label><br>
                            <input type="file" id="imageFiles" name="images" multiple>
                            <ul id="imageList" class="imageList"></ul>
                            <input type="hidden" name="postId" value="${post.postId}">
                            <button type="submit">등록</button>
                        </form>
                    `);

                    // 기존 이미지 삭제 버튼 포함
                    commentDiv.find('.image-container').each(function () {
                        const imageId = $(this).data('image-id');
                        const commentId = $(this).closest('.commentDiv').data('comment-id')
                        $(this).append(`
                            <button type="button" class="removeImage" onclick="removeCommentImage('${'${commentId}'}','${'${imageId}'}', this)">기존 이미지 삭제</button>
                        `);
                    });

                    // 취소 버튼 이벤트 핸들러 추가
                    $('.cancelEditButton').click(function () {
                        $(`#editInputContainer-${'${commentId}'}`).empty();
                        $(`.removeImage`).empty();
                    });
                });

                $('#imageFiles').on('change', (event) => {
                    const imageList = $('#imageList');
                    imageList.empty();

                    fileList = Array.from(event.target.files);

                    fileList.forEach((file, index) => {
                        const li = $('<li>').addClass('image').text(file.name);
                        const button = $('<button>').text("삭제").on('click', () => {
                            removeFile(index);
                            li.remove();
                        });

                        li.append(button);
                        imageList.append(li);
                    });
                });

                const removeFile = (index) => {
                    fileList.splice(index, 1);
                    const dataTransfer = new DataTransfer();
                    fileList.forEach(file => dataTransfer.items.add(file));
                    $('#imageFiles')[0].files = dataTransfer.files;

                    updateFileList();
                }

                const updateFileList = () => {
                    const imageList = $('#imageList');
                    imageList.empty();

                    fileList.forEach((file, index) => {
                        const li = $('<li>').addClass('image').text(file.name);
                        const button = $('<button>').text("삭제").on('click', () => {
                            removeFile(index);
                            li.remove();
                        });

                        li.append(button);
                        imageList.append(li);
                    });
                }

                $('#commentForm').on('submit', (event) => {
                    const imageFiles = $('#imageFiles')[0].files;
                    
                    try {
                        checkValidImages(imageFiles);
                    } catch (error) {
                        event.preventDefault(); // 폼 제출 중지
                        alert("댓글 등록을 실패하였습니다.");
                        return false;
                    }
                });

                const checkValidImages = (imageFiles) => {
                    const allowedExtensions = ['jpg', 'jpeg', 'png'];
                    const maxFileSize = 10 * 1024 * 1024; // 10MB
                    let totalSize = 0;

                    for (const imageFile of imageFiles) {
                        const fileExtension = imageFile.name.split('.').pop().toLowerCase(); // 파일 확장자 추출

                        if (!allowedExtensions.includes(fileExtension)) {
                            throw new Error(`허용되지 않는 파일 형식입니다 (jpg, jpeg, png) : ${imageFile.name}`);
                        }

                        totalSize += imageFile.size; // 총 파일 사이즈

                        if (imageFile.size > maxFileSize) {
                            throw new Error(`최대 이미지 크기는 10MB 입니다. : ${imageFile.size}`);
                        }
                    }

                    if (totalSize > maxFileSize) {
                        throw new Error("전체 파일 크기가 10MB를 초과합니다.");
                    }
                }

                if (userId) {
                    $.ajax({
                        url: '/likes/post/check',
                        type: 'GET',
                        data: {postId: ${post.postId}},
                        success: (response) => {
                            if (response) {
                                $('#postLikeButton').text('좋아요 취소').data('liked', true);
                            } else {
                                $('#postLikeButton').text('좋아요').data('liked', false);
                            }
                        },
                        error: (error) => {
                            alert("페이지를 불러오던 도중 문제가 생겼습니다.");
                            window.location.href = "/"
                        }
                    });
                }
            });

        </script>

        <h1>게시판 작성</h1>
        <h2>제목 : ${post.title}</h2>
        <h4>작성자 : ${post.name}</h4>
        <h4>조회수 : ${post.views}</h4>
        <h4 id="postLikes">좋아요 수 : ${post.likes}</h4>
        <h3>본문</h3>
        <p>${post.content}</p>
        생성일 : <fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/> <br>
        최종 수정일 : <fmt:formatDate value="${post.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/>

        <button onclick=window.location.href="/community?page=1&sort=DATE&row=10">목록으로 돌아가기</button>
        <button onclick=window.location.href="/">홈으로 돌아가기</button>

        <c:if test="${ImageDetailsResDTOList != null && fn:length(ImageDetailsResDTOList) > 0}">
            <c:forEach var="file" items="${ImageDetailsResDTOList}">
                <img src="${file.imagePath}" alt="${file.storedName}"/>
            </c:forEach>
        </c:if>

        <c:if test="${currentUserId != null}">
            <form action="/comment" method="post" enctype="multipart/form-data">
                <sec:csrfInput/>
                <label for="content">댓글</label><br>
                <input type="text" id="content" name="content" required><br><br>

                <input type="hidden" id="postId" name="postId" value="${post.postId}">

                <label for="imageFiles">이미지 첨부파일</label><br>
                <input type="file" id="imageFiles" name="images" multiple>
                <ul id="imageList" class="imageList"></ul>

                <button id="commentForm" type="submit">등록</button>
            </form>

            <button id="postLikeButton" type="button" data-liked="false" onclick="handlePostLikeButton()">좋아요</button>
        </c:if>

        <c:forEach var="comment" items="${commentDetailResDTOList}">
            <c:choose>
                <c:when test="${comment.parentId != null}">
                    <div class="commentDiv" data-comment-id="${comment.commentId}" data-comment-content="${comment.content}">
                        <p class="topLevelComment">
                                ${comment.name} : ${comment.content}
                                ${comment.createdAt}
                        </p>
                        <p id="comment-likes-${comment.commentId}">좋아요 개수 : ${comment.likes}</p>
                        <c:if test="${comment.images != null && fn:length(comment.images) > 0}">
                            <c:forEach var="file" items="${comment.images}">
                                <div class="image-container" data-image-id="${file.imageId}">
                                    <img width="100" height="100" src="${file.imagePath}" alt="${file.storedName}"/>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${currentUserId != null}">
                            <button type="button" class="replyButton">답글 쓰기</button>
                            <c:if test="${!comment.hasLike}">
                                <button id="comment-like-button-${comment.commentId}" type="button" data-comment-id="${comment.commentId}"
                                        data-liked=${comment.hasLike} onclick="handleCommentLikeButton(${comment.commentId})">좋아요
                                </button>
                            </c:if>
                            <c:if test="${comment.hasLike}">
                                <button id="comment-like-button-${comment.commentId}" type="button" data-comment-id="${comment.commentId}"
                                        data-liked=${comment.hasLike} onclick="handleCommentLikeButton(${comment.commentId})">좋아요 취소
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${currentUserId == comment.userId}">
                            <button type="button" class="editButton">수정</button>
                            <button type="button" class="cancelEditButton">수정 취소</button>
                            <button onclick=window.location.href=`/comment/${comment.postId}/remove?commentId=${comment.commentId}`>삭제</button>
                        </c:if>
                        <div class="replyInputContainer" id="replyInputContainer-${comment.commentId}"></div>
                        <div class="editInputContainer" id="editInputContainer-${comment.commentId}"></div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="commentDiv" data-comment-id="${comment.commentId}" data-comment-content="${comment.content}">
                        <p class="comment">
                                ${comment.name} : ${comment.content}
                                ${comment.createdAt}
                        </p>
                        <p id="comment-likes-${comment.commentId}">좋아요 개수 : ${comment.likes}</p>
                        <c:if test="${comment.images != null && fn:length(comment.images) > 0}">
                            <c:forEach var="file" items="${comment.images}">
                                <div class="image-container" data-image-id="${file.imageId}">
                                    <img width="100" height="100" src="${file.imagePath}" alt="${file.storedName}"/>
                                </div>
                            </c:forEach>
                        </c:if>
                        <c:if test="${currentUserId != null}">
                            <button type="button" class="replyButton">답글 쓰기</button>
                            <c:if test="${!comment.hasLike}">
                                <button id="comment-like-button-${comment.commentId}" type="button" data-comment-id="${comment.commentId}"
                                        data-liked=${comment.hasLike} onclick="handleCommentLikeButton(${comment.commentId})">좋아요
                                </button>
                            </c:if>
                            <c:if test="${comment.hasLike}">
                                <button id="comment-like-button-${comment.commentId}" type="button" data-comment-id="${comment.commentId}"
                                        data-liked=${comment.hasLike} onclick="handleCommentLikeButton(${comment.commentId})">좋아요 취소
                                </button>
                            </c:if>
                        </c:if>
                        <c:if test="${currentUserId == comment.userId}">
                            <button type="button" class="editButton">수정</button>
                            <button type="button" class="cancelEditButton">수정 취소</button>
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
