const imageInput = document.getElementById('images');
const selectedImageNamesContainer = document.getElementById('selectedImageNamesContainer');
const selectedImageNames = []; // 이미지 파일명을 저장하는 배열

imageInput.addEventListener('change', () => {
    // 기존 목록 초기화
    selectedImageNamesContainer.innerHTML = '';

    // 선택한 이미지 파일들의 목록을 업데이트
    for (let i = 0; i < imageInput.files.length; i++) {
        const imageFile = imageInput.files[i];

        // 이미지 파일명을 목록에 추가해서 표시
        const imageItem = document.createElement('li');
        imageItem.textContent = imageFile.name;
        selectedImageNamesContainer.appendChild(imageItem);
    }
});


reviewForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const title = reviewForm.title.value;
    const content = reviewForm.content.value;
    const images = reviewForm.images.files;

    if (title.trim() === '' || content.trim() === '') {
        alert('제목과 내용을 입력해주세요.');
        return; // 제목 또는 내용이 비어있으면 더 이상 진행하지 않음
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);

    // 여러 개의 이미지 파일을 FormData에 추가
    for (let i = 0; i < images.length; i++) {
        formData.append('images', images[i]);
    }

    try {
        const response = await fetch('/api/review-posts', {
            method: 'POST',
            body: formData,
        });

        if (response.ok) {
            // 게시글 작성 완료 팝업 메시지 표시
            const confirmation = confirm('게시글이 작성되었습니다. 확인을 누르면 목록으로 이동합니다.');

            if (confirmation) {
                // 확인을 누를 경우 /view/reviewPost 페이지로 리디렉션
                window.location.href = '/view/review-post';
            }
        } else {
            alert('게시글 작성에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error creating review post:', error);
        alert('게시글 작성 중 오류가 발생했습니다.');
    }
});

// 취소 버튼 클릭 시 처리
const cancelButton = document.getElementById('cancel-button');
cancelButton.addEventListener('click', () => {
    // 취소 시 view/reviewPost 페이지로 리디렉션
    window.location.href = '/view/review-post';
});
