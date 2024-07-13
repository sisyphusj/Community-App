$(() => {
    $('#postSubmitBtn').on("click", () => {
        const title = $('#title').val();
        const content = $('#content').val();

        // 제목 내용 유효성 검사
        if (!title || !content) {
            alert("제목과 내용 모두 입력해주세요.");
            return;
        }

        // 제목 최대 길이 검사
        if (title.length > 50) {
            alert("제목의 최대 글자 수는 50글자입니다.");
            return;
        }

        // 본문 최대 길이 검사
        if (content.length > 500) {
            alert("본문의 최대 글자 수는 500글자입니다.");
            return;
        }

        const postData = {
            title: title,
            content: content,
            hasImage: window.uploadedImages ? "Y" : "N",
            imageDetailReqDTOList: window.uploadedImages
        };

        const csrfToken = $('input[name="_csrf"]').val();

        // 버튼 비활성화
        $('#postSubmitBtn').prop('disabled', true).text('등록 중..');

        $.ajax({
            url: "/community/add",
            type: "POST",
            data: JSON.stringify(postData),
            contentType: "application/json; charset=utf-8",
            beforeSend: (xhr) => {
                xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
            },
            success: () => {
                alert('게시글이 등록되었습니다.');
                window.location.href = '/community';
            },
            error: (error) => {
                console.error(error);
                alert("게시글 등록 중 오류가 발생했습니다. 다시 시도하여 주십시오.");
                $('#postSubmitBtn').prop('disabled', false).text('등록');
            }
        });
    });
});
