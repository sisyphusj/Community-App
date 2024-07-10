<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>게시판</title>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript">
        $(() => {
            $.getJSON("board/posts", (data) => {
                const postsHtml = data.map(post => {
                    return `
                    <div class="post-thumbnail" id="post-${'${post.boardId}'}">
                        <h3>${'${post.title}'}</h3>
                        <p>작성자: ${'${post.author}'}</p>
                        <p>작성일: ${'${post.createdDate}'}</p>
                    </div>
                    `;
                }).join('');

                $('#posts').html(postsHtml);
            })

            $('#createPostBtn').click(function () {
                window.location.href = '/board/new';
            });
        });
    </script>

    <style>
        .post-thumbnail {
            display: flex;
        }
    </style>
</head>
<body>
<h1>게시판</h1>

<button id="createPostBtn">게시글 쓰기</button>

<div id="posts"></div>

</body>
</html>
