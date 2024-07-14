$(() => {
    $.ajax({
        url: `/images?postId=${postId}`,
        type: "GET",
        success: (data) => {
            renderImages(data);
        },
        error: (error) => {
            console.error(error);
            alert("이미지 불러오기 중 오류가 발생하였습니다.");
        }
    });

    function renderImages(images) {
        const imagesContainer = $('#postImages');
        imagesContainer.empty();

        images.forEach(image => {
            const imgElement = $('<img>').attr('src', image.imagePath).attr('alt', image.originalName);
            imagesContainer.append(imgElement);
        });
    }
});