window.uploadedImages = [];

$(() => {
    $('#imageFiles').on("change", () => {
        const formData = new FormData(); // 폼 데이터
        const imageFiles = $('#imageFiles')[0].files; // 이미지 첨부파일 배열
        const allowedExtensions = ['jpg', 'jpeg', 'png'];
        const maxFileSize = 10 * 1024 * 1024; // 10MB
        let totalSize = 0;

        if (!imageFiles.length) {
            alert("이미지가 선택되지 않았습니다.");
            return;
        }

        // 폼 데이터에 이미지 첨부파일 삽입 List<MultipartFile>로 매핑
        for (const imageFile of imageFiles) {
            const fileExtension = imageFile.name.split('.').pop().toLowerCase(); // 파일 확장자 추출

            if (!allowedExtensions.includes(fileExtension)) {
                alert(`허용되지 않는 파일 형식입니다 (jpg, jpeg, png) : ${imageFile.name}`);
                return;
            }

            totalSize += imageFile.size; // 총 파일 사이즈

            if (imageFile.size > maxFileSize) {
                alert(`최대 이미지 크기는 10MB 입니다. : ${imageFile.size}`);
                return;
            }

            formData.append("uploadFiles", imageFile);
        }

        if (totalSize > maxFileSize) {
            alert("전체 파일 크기가 10MB를 초과합니다.");
            return;
        }

        // CSRF 토큰 삽입
        const csrfToken = $('input[name="_csrf"]').val();
        formData.append('_csrf', csrfToken);

        $.ajax({
            url: "/images/upload",
            type: "POST",
            data: formData,
            processData: false, // FormData 객체 그대로 전송
            contentType: false,
            success: (data) => {
                window.uploadedImages = data;
                const imageList = $('#imageList');
                imageList.empty();

                // UI에 등록한 이미지가 식별될 수 있도록 설정
                $.each(data, (index, image) => {
                    const item = $('<li></li>').text(`${image.originalName} (${image.size} bytes) / ${image.imagePath}`);
                    imageList.append(item);
                });
            },
            error: (error) => {
                console.error(error);
                alert("이미지 업로드에 실패하였습니다. 다시 시도해주세요");
            }
        });
    });
});
