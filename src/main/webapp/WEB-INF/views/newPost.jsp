<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <title>여행지 게시판</title>
        <%@ include file="include/head.jsp" %>
    </head>
    <body>
        <div class="container mt-5">
            <h1 class="mb-4">게시판 작성</h1>
            <form id="postForm" action="/community/posts" method="post" enctype="multipart/form-data">
                <sec:csrfInput/>
                <div class="form-group">
                    <label for="category">카테고리</label>
                    <select name="category" id="category">
                        <option value="TouristSpot">관광지 리뷰</option>
                        <option value="Food">음식 리뷰</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="title">제목</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>
                <div class="form-group">
                    <label for="content">본문</label>
                    <textarea class="form-control" id="content" name="content" rows="10" required></textarea>
                </div>
                <div class="form-group">
                    <label for="imageFiles">이미지 첨부파일</label>
                    <input type="file" class="form-control-file" id="imageFiles" name="images" multiple>
                    <ul id="imageList" class="list-group mt-2"></ul>
                </div>
                <input type="hidden" name="boardType" value="NORMAL"/>
                <button type="submit" class="btn btn-primary mt-3" id="postSubmitBtn">등록</button>
            </form>
            <button id="back-to-list">목록으로 돌아가기</button>
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

                $('#back-to-list').click(function () {
                    location.href = '/community?page=1&sort=DATE&keywordType=&keyword=&row=10'
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
            });
        </script>
    </body>
</html>
