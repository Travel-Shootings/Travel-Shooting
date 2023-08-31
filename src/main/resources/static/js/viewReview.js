const urlParams = new URLSearchParams(window.location.search);
const postTitleElement = document.getElementById('post-title');
const postContentElement = document.getElementById('post-content');
const imageContainer = document.getElementById('image-container'); // 이미지를 표시할 컨테이너
const deleteButton = document.getElementById('delete-button'); // 삭제 버튼
const editButton = document.getElementById('edit-button');
// 좋아요 버튼 요소를 가져옵니다.
const likeButton = document.getElementById('like-button');
const likeIcon = document.getElementById('like-icon');

// URL 경로에서 reviewPostId 추출
const reviewPostId = window.location.pathname.split('/').pop();

// 서버에서 게시물 데이터 조회
async function fetchPostData() {
    try {
        const response = await fetch(`/api/reviewPosts/${reviewPostId}`);
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Error fetching post data:', error);
        return null;
    }
}

// 이미지 데이터를 화면에 표시하는 함수
function renderImages(imageUrls) {
    for (const imageUrl of imageUrls) {
        const imageElement = document.createElement('img');
        imageElement.src = imageUrl;
        imageElement.classList.add('post-image'); // 이미지 스타일을 위한 클래스 추가
        imageContainer.appendChild(imageElement);
    }
}

// 페이지 로드 시 게시물 데이터 조회 및 화면에 표시
async function init() {
    const postData = await fetchPostData();

    if (postData) {
        postTitleElement.textContent = postData.title;
        postContentElement.textContent = postData.content;

        // 이미지 URL 정보를 이용해 이미지를 화면에 표시
        renderImages(postData.imageUrls);


        // 좋아요 수를 가져와서 UI에 표시
        const likeCountElement = document.getElementById('like-count');
        likeCountElement.textContent = postData.likeCounts; // 수정된 부분

        const imageElements = document.querySelectorAll('.post-image');
        for (const imageElement of imageElements) {
            imageElement.style.width = '300px';
            imageElement.style.height = '200px';
            imageElement.style.marginBottom = '20px';
        }

        // 삭제 버튼 이벤트 핸들러 등록
        deleteButton.addEventListener('click', deleteReviewPost);

        // 수정 버튼 이벤트 핸들러 등록
        editButton.addEventListener('click', () => {
            window.location.href = `/view/reviewPost/update/${reviewPostId}`;
        });

    } else {
        postTitleElement.textContent = 'Error';
        postContentElement.textContent = 'Failed to load post data.';
    }
}

// 후기 게시글 삭제 요청 함수
async function deleteReviewPost() {
    const confirmation = confirm('게시글을 삭제하시겠습니까?');

    if (confirmation) {
        try {
            const response = await fetch(`/api/reviewPosts/${reviewPostId}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                // 삭제 성공 시 페이지 이동
                window.location.href = '/view/reviewPost';
            } else {
                console.error('Error deleting review post:', response.statusText);
            }
        } catch (error) {
            console.error('Error deleting review post:', error);
        }
    }
}

// 좋아요 버튼 클릭 이벤트를 처리합니다.
likeButton.addEventListener('click', async () => {
    try {
        const response = await fetch(`/api/reviewPosts/like/${reviewPostId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (response.ok) {
            const data = await response.json();
            if (data.status === 200) {
                alert('좋아요가 등록되었습니다.');
            } else {
                alert(data.message); // 에러 메시지를 표시합니다.
            }
        } else {
            console.error('Error adding like:', response.statusText);
        }
        window.location.reload();
    } catch (error) {
        console.error('Error adding like:', error);
    }
});




init();
