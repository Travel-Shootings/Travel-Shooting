const submitButton = document.getElementById('submit-button'); // 수정 페이지의 "저장" 버튼
const cancelButton = document.getElementById('cancel-button'); // 수정 페이지의 "취소" 버튼

// URL 경로에서 reviewPostId 추출
const reviewPostId = window.location.pathname.split('/').pop();

submitButton.addEventListener('click', updateReviewPost);
cancelButton.addEventListener('click', cancelUpdate);

// 후기 게시글 수정 요청 함수
async function updateReviewPost() {
    // 필요한 데이터 수집 및 validation

    try {
        const response = await fetch(`/api/reviewPosts/${reviewPostId}`, {
            method: 'PATCH',
            // 필요한 요청 데이터 추가
        });

        if (response.ok) {
            // 수정 성공 시 처리
            window.location.href = '/view/reviewPost'; // 수정된 후기 게시글 페이지로 이동
        } else {
            console.error('Error updating review post:', response.statusText);
        }
    } catch (error) {
        console.error('Error updating review post:', error);
    }
}

// 수정 취소 함수
function cancelUpdate() {
    // 수정 취소 시 처리
    window.location.href = '/view/reviewPost'; // 이전에 보던 후기 게시글 페이지로 이동
}
