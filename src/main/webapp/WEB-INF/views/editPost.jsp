<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
        <link href="/static/css/material-kit.min.css" rel="stylesheet"/>
        <script src="/static/js/material-kit.min.js"></script>
    </head>
    <body>
        <%-- 게시글 정보 --%>
        <c:set var="post" value="${postDetailResDTO}"/>

        <div class="container mt-5">
            <h1>게시글 수정</h1>

            <form id="postForm" action="/community/posts/edit" method="post" enctype="multipart/form-data" class="needs-validation" novalidate>
                <sec:csrfInput/>

                <%-- postId 추가 --%>
                <input type="hidden" id="postId" name="postId" value="${post.postId}">

                <%-- 제목 --%>
                <div class="form-group">
                    <label for="title">제목</label>
                    <input type="text" class="form-control" id="title" name="title" value="${post.title}" required>
                    <div class="invalid-feedback">
                        제목을 입력하세요.
                    </div>
                </div>

                <%-- 본문 --%>
                <div class="form-group">
                    <label for="content">본문</label>
                    <textarea class="form-control" id="content" name="content" rows="10" required>${post.content}</textarea>
                    <div class="invalid-feedback">
                        본문을 입력하세요.
                    </div>
                </div>

                <%-- 기존 첨부 이미지 불러오기 --%>
                <c:if test="${ImageDetailsResDTOList != null && fn:length(ImageDetailsResDTOList) > 0}">
                    <h3>기존 이미지</h3>
                    <div class="row">
                        <c:forEach var="file" items="${ImageDetailsResDTOList}">
                            <div class="col-md-4 mb-3">
                                <div class="card">
                                    <img class="card-img-top" src="${file.imagePath}" alt="${file.storedName}" style="height: 200px; object-fit: cover;">
                                    <div class="card-body">
                                        <button type="button" class="btn btn-danger btn-sm" onclick="removeImage('${file.imageId}', this)">삭제</button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>

                <%-- 새로운 첨부 이미지 불러오기 --%>
                <div class="form-group">
                    <label for="imageFiles">새로운 이미지 첨부파일</label>
                    <input type="file" class="form-control" id="imageFiles" name="images" multiple>
                    <ul id="imageList" class="list-group mt-2"></ul>
                </div>

                <%-- 게시판 타입(일반 게시판) --%>
                <input type="hidden" name="boardType" value="NORMAL"/>

                <button type="submit" class="btn btn-primary">등록</button>

                <button type="button" class="btn btn-danger" onclick="location.href='/community/NORMAL/posts/${post.postId}/remove'">게시글 삭제</button>
                <button type="button" class="btn btn-secondary" onclick="location.href='/'">메인으로 돌아가기</button>
            </form>

        </div>

        <script>
            $(function () {
                let fileList = [];

                $('#imageFiles').on('change', (event) => {
                    const imageList = $('#imageList');
                    imageList.empty();

                    fileList = Array.from(event.target.files);

                    fileList.forEach((file, index) => {
                        const li = $('<li>').addClass('list-group-item d-flex justify-content-between align-items-center').text(file.name);
                        const button = $('<button>').addClass('btn btn-danger btn-sm').text("삭제").on('click', () => {
                            removeFile(index);
                            li.remove();
                        });

                        li.append(button);
                        imageList.append(li);
                    });
                });

                $('#postForm').on('submit', (event) => {
                    const title = $('#title').val();
                    const content = $('#content').val();
                    const imageFiles = $('#imageFiles')[0].files;

                    try {
                        if (imageFiles.length > 0) {
                            checkValidImages(imageFiles);
                        }

                        checkValidTitleAndContent(title, content);
                    } catch (error) {
                        event.preventDefault(); // 폼 제출 중지
                        alert(error.message);
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

                const checkValidTitleAndContent = (title, content) => {
                    // 제목 내용 유효성 검사
                    if (!title || !content) {
                        throw new Error("제목과 내용 모두 입력해주세요.");
                    }

                    // 제목 최대 길이 검사
                    if (title.length > 50) {
                        throw new Error("제목의 최대 글자 수는 50글자입니다.");
                    }

                    // 본문 최대 길이 검사
                    if (content.length > 500) {
                        throw new Error("본문의 최대 글자 수는 500글자입니다.");
                    }
                }

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
                        const li = $('<li>').addClass('list-group-item d-flex justify-content-between align-items-center').text(file.name);
                        const button = $('<button>').addClass('btn btn-danger btn-sm').text("삭제").on('click', () => {
                            removeFile(index);
                            li.remove();
                        });

                        li.append(button);
                        imageList.append(li);
                    });
                }

            });

            const removeImage = (imageId, element) => {
                const csrfToken = $('input[name="_csrf"]').val();
                $.ajax({
                    url: `/images/post/remove?postId=${post.postId}&imageId=${imageId}`,
                    type: "GET",
                    beforeSend: (xhr) => {
                        xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
                    },
                    success: function () {
                        alert("이미지가 제거되었습니다.");
                        $(element).closest('div.card').remove();
                    },
                    error: function (error) {
                        console.error(error);
                        alert("이미지 삭제 중 오류가 발생하였습니다.");
                    }
                });
            }
        </script>
    </body>
</html>
