// review_post.js

const reviewListElement = document.getElementById('reviewList');

// 서버에서 후기 게시글 조회
async function fetchReviewPosts() {
    try {
        const response = await fetch('/api/review-posts'); // 수정된 API 엔드포인트를 사용하도록 수정
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Error fetching review posts:', error);
        return [];
    }
}

// 서버에서 게시글 좋아요 정보 조회
async function fetchPostLikes(postId) {
    try {
        const response = await fetch(`/api/review-posts/${postId}/likes`); // 수정된 API 엔드포인트를 사용하도록 수정
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Error fetching post likes:', error);
        return null;
    }
}

// 후기 게시글 목록을 UI에 렌더링
async function renderReviewPosts(reviewPosts) {
    const tableBody = document.querySelector('.board-table tbody');

    for (const [index, post] of reviewPosts.entries()) {
        const row = document.createElement('tr');

        const numCell = document.createElement('td');
        numCell.textContent = index + 1;
        row.appendChild(numCell);

        const titleCell = document.createElement('th');
        titleCell.classList.add('th-title');
        const titleLink = document.createElement('a');
        titleLink.href = `/view/review-post/${post.id}`;
        titleLink.textContent = `${post.title}`;
        titleCell.appendChild(titleLink);
        row.appendChild(titleCell);

        console.log(post);

        const nicknameCell = document.createElement('td');
        nicknameCell.textContent = post.nickName;
        row.appendChild(nicknameCell);

        const likeCell = document.createElement('td');
        likeCell.textContent = post.likeCounts;
        row.appendChild(likeCell);

        const dateCell = document.createElement('td');

// 날짜 정보가 유효한 경우에만 포맷팅 수행
        if (new Date(post.createdAt).getTime()) {
            const formattedDate = new Date(post.createdAt).toLocaleDateString('en-US', {
                year: 'numeric',
                month: 'short',
                day: 'numeric',
            });
            dateCell.textContent = formattedDate;
        } else {
            dateCell.textContent = 'Invalid Date';
        }

        row.appendChild(dateCell);

        tableBody.appendChild(row);
    }
}
// 페이지 로드 시 후기 게시글 조회 및 렌더링
async function init() {
    const reviewPosts = await fetchReviewPosts();
    renderReviewPosts(reviewPosts);
}

init();

