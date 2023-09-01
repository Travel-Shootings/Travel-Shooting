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

const submitButton = document.getElementById('submit-button'); // 수정 페이지의 "저장" 버튼
const cancelButton = document.getElementById('cancel-button'); // 수정 페이지의 "취소" 버튼

// URL 경로에서 reviewPostId 추출
const reviewPostId = window.location.pathname.split('/').pop();

submitButton.addEventListener('click', async (event) => {
    event.preventDefault(); // 폼 전송 방지

    await updateReviewPost(); // 수정 요청 함수 호출
});
cancelButton.addEventListener('click', cancelUpdate);

// 페이지 로드 시 게시물 데이터 조회 및 입력 필드에 채우기
async function loadPostData() {
    try {
        const response = await fetch(`/api/reviewPosts/${reviewPostId}`);
        const postData = await response.json();

        // 입력 필드에 데이터 채우기
        document.getElementById('title').value = postData.title;
        document.getElementById('content').value = postData.content;

    } catch (error) {
        console.error('Error loading post data:', error);
    }
}
// 수정 취소 함수
function cancelUpdate() {
    // 수정 취소 시 처리
    window.location.href = `/view/reviewPost/${reviewPostId}`; // 이전에 보던 후기 게시글 페이지로 이동
}

// 후기 게시글 수정 요청 함수
async function updateReviewPost() {
    // 필요한 데이터 수집 및 validation
    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const images = document.getElementById('images').files;

    // 입력값 validation
    if (!title.trim() || !content.trim()) {
        alert('제목과 내용을 입력해주세요.');
        return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);

    // 여러 개의 이미지 파일을 FormData에 추가
    for (let i = 0; i < images.length; i++) {
        formData.append('images', images[i]);
    }

    try {
        const response = await fetch(`/api/reviewPosts/${reviewPostId}`, {
            method: 'PUT', // PUT 메소드로 변경
            body: formData,
        });

        if (response.status === 200) {
            // 수정 성공 시 처리
            const confirmation = confirm('게시글이 수정되었습니다. 확인을 누르면 목록으로 이동합니다.');

            if (confirmation) {
                // 확인을 누를 경우 /view/reviewPost 페이지로 리디렉션
                window.location.href = '/view/reviewPost';
            }
        } else {
            console.error('Error updating review post:', response.statusText);
        }
    } catch (error) {
        console.error('Error updating review post:', error);
    }
}


// 페이지 로드 시 기존 데이터 불러오기
loadPostData();
