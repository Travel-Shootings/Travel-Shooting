// review_post.js
document.addEventListener('DOMContentLoaded', () => {
    const prevPageButton = document.getElementById('prevPage');
    const nextPageButton = document.getElementById('nextPage');
    let currentPage = 1;

    async function init() {
        await fetchDataAndRender(currentPage);
    }

    init();

    prevPageButton.addEventListener('click', async () => {
        if (currentPage > 1) {
            currentPage--;
            await fetchDataAndRender(currentPage);
            console.log('이전 페이지 버튼 클릭됨');
        }
    });

    nextPageButton.addEventListener('click', async () => {
        currentPage++;
        await fetchDataAndRender(currentPage);
        console.log('다음 페이지 버튼 클릭됨');
    });

// 페이지에 맞는 데이터를 서버에서 요청하고 렌더링하는 함수
async function fetchDataAndRender(page) {
    try {
        // 서버에서 데이터를 가져올 때 페이지 정보를 전달해야 합니다.
        const response = await fetch(`/api/review-posts/page?page=${page}&size=6`);
        const data = await response.json();

        // 가져온 데이터를 renderReviewPosts 함수로 전달하여 화면에 렌더링합니다.
        renderReviewPosts(data.content); // "content"는 페이지 내용입니다.

        // 여기에서 페이징 컨트롤을 업데이트할 수 있습니다.
    } catch (error) {
        console.error('Error fetching review posts:', error);
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

    // 테이블 내용 초기화
    tableBody.innerHTML = '';

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
});
