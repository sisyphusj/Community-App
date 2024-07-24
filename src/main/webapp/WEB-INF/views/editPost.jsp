<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <script src="<c:url value='https://code.jquery.com/jquery-3.7.1.min.js'/>"></script>
        <title>게시글 수정</title>
    </head>
    <body>
        <c:set var="post" value="${postDetailResDTO}"/>
        <script>
            $(function () {
                let fileList = [];

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
                        alert("게시글 등록을 실패하였습니다.");
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
                        const li = $('<li>').addClass('image').text(file.name);
                        const button = $('<button>').text("삭제").on('click', () => {
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
                    url: `/images/remove?imageId=${'${imageId}'}`,
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
        </script>
        <h1>게시글 수정</h1>
        <form id="postForm" action="/community/posts/edit" method="post" enctype="multipart/form-data">
            <sec:csrfInput/>
            <input type="hidden" id="postId" name="postId" value="${post.postId}">
            <label for="title">제목</label><br>
            <input type="text" id="title" name="title" value="${post.title}" required><br><br>

            <label for="content">본문</label><br>
            <textarea id="content" name="content" rows="10" required>${post.content}</textarea><br><br>

            <c:if test="${ImageDetailsResDTOList != null && fn:length(ImageDetailsResDTOList) > 0}">
                <h3>기존 이미지</h3><br>
                <c:forEach var="file" items="${ImageDetailsResDTOList}">
                    <div class="image-container">
                        <img src="${file.imagePath}" alt="${file.storedName}"/>
                        <button type="button" onclick="removeImage('${file.imageId}', this)">삭제</button>
                    </div>
                </c:forEach>
            </c:if>

            <label for="imageFiles">새로운 이미지 첨부파일</label><br>
            <input type="file" id="imageFiles" name="images" multiple>
            <ul id="imageList" class="imageList"></ul>

            <button type="submit" id="postSubmitBtn">등록</button>
        </form>

        <button type="button" onclick="location.href= `/community/posts/remove?postId=${post.postId}`">게시글 삭제</button>
    </body>
</html>
