$(() => {
    $('#postSubmitBtn').on("click", () => {
        const title = $('#title').val();
        const content = $('#content').val();
        let formData = new FormData(); // 폼 데이터

        const imageFiles = $('#imageFiles')[0].files; // 이미지 파일들

        try {
            if (imageFiles.length > 0) {
                formData = checkValidImages(imageFiles, formData);
                formData.append('hasImage', "Y");
            } else {
                formData.append('hasImage', "N");
            }

            checkValidTitleAndContent(title, content);

            formData.append('title', title);
            formData.append('content', content);
            const csrfToken = $('input[name="_csrf"]').val();

            // 버튼 비활성화
            $('#postSubmitBtn').prop('disabled', true).text('등록 중..');

            $.ajax({
                url: "/community/posts",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                beforeSend: (xhr) => {
                    xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
                },
                success: (response) => {
                    alert('게시글이 등록되었습니다.');
                    window.location.href = '/community';
                },
                error: (error) => {
                    console.error(error);
                    alert("게시글 등록 중 오류가 발생했습니다. 다시 시도하여 주십시오.");
                    $('#postSubmitBtn').prop('disabled', false).text('등록');
                }
            });
        } catch (error) {
            alert(error.message);
        }
    });

    function checkValidImages(imageFiles, formData) {
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

            formData.append("images", imageFile);
        }

        if (totalSize > maxFileSize) {
            throw new Error("전체 파일 크기가 10MB를 초과합니다.");
        }

        return formData;
    }

    function checkValidTitleAndContent(title, content) {

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
