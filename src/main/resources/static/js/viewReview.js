// viewReview.js

const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('postId'); // URL 매개변수에서 postId 값을 가져옴

const postTitleElement = document.getElementById('post-title');
const postContentElement = document.getElementById('post-content');

// 서버에서 게시물 데이터 조회
async function fetchPostData() {
    try {
        const response = await fetch(`/api/reviewPosts/${postId}`); // 수정된 API 엔드포인트를 사용하도록 수정
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Error fetching post data:', error);
        return null;
    }
}

// 페이지 로드 시 게시물 데이터 조회 및 화면에 표시
async function init() {
    const postData = await fetchPostData();

    if (postData) {
        postTitleElement.textContent = postData.title;
        postContentElement.textContent = postData.content;
    } else {
        // 예외 처리 로직: 데이터를 불러오지 못한 경우에 대한 처리
        postTitleElement.textContent = 'Error';
        postContentElement.textContent = 'Failed to load post data.';
    }
}

init();
