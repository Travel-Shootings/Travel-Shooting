// review_post.js
document.addEventListener('DOMContentLoaded', () => {
    const prevPageButtonPagination = document.getElementById('prevPage');
    const nextPageButtonPagination = document.getElementById('nextPage');

    let currentPage = 0;
    let totalPage = 0; // totalPage 변수를 추가

    async function init() {
        await fetchDataAndRender(currentPage);
        updateTotalPages(); // 추가된 부분
    }
    init();

    // 이전 페이지 버튼 클릭 이벤트
    prevPageButtonPagination.addEventListener('click', async () => {
        if (currentPage > 0) {
            currentPage--;
            await fetchDataAndRender(currentPage);
            updateTotalPages();
        }
    });

// 다음 페이지 버튼 클릭 이벤트
    nextPageButtonPagination.addEventListener('click', async () => {
        if (currentPage < totalPage - 1) {
            currentPage++;
            await fetchDataAndRender(currentPage);
            updateTotalPages();
        }
    });

// updateTotalPages 함수 내부를 수정
    async function updateTotalPages() {
        try {
            const response = await fetch(`/api/review-posts/page?page=0&size=6`); // 첫 페이지의 결과만 요청
            const data = await response.json();
            totalPage = data.totalPages;

            // 페이지 숫자를 렌더링
            const paginationContainer = document.querySelector('.pagination');
            paginationContainer.innerHTML = '';

            const startPage = Math.max(currentPage - 2, 0);
            const endPage = Math.min(startPage + 4, totalPage - 1);

            for (let i = startPage; i <= endPage; i++) {
                const pageButton = document.createElement('button');
                pageButton.textContent = i + 1;
                pageButton.classList.add('page-number');

                if (i === currentPage) {
                    pageButton.classList.add('active'); // 현재 페이지 표시
                }

                pageButton.addEventListener('click', async () => {
                    currentPage = i;
                    await fetchDataAndRender(currentPage);
                    updateTotalPages();
                });

                paginationContainer.appendChild(pageButton);
            }

        } catch (error) {
            console.error('Error fetching total pages:', error);
        }
    }



    async function fetchDataAndRender(page) {
        try {
            const response = await fetch(`/api/review-posts/page?page=${page}&size=6`);
            const data = await response.json();
            const reversedData = data.content; // 원본 데이터를 복사하고 역순으로 정렬
            renderReviewPosts(reversedData);
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
            numCell.textContent = post.id;
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
            const options = { year: 'numeric', month: 'numeric', day: 'numeric' };
            if (new Date(post.createdAt).getTime()) {
                const formattedDate = new Date(post.createdAt).toLocaleDateString('ko-KR', options).replace(/\./g, '.');
                dateCell.textContent = formattedDate;
            } else {
                dateCell.textContent = 'Invalid Date';
            }


            row.appendChild(dateCell);

            tableBody.appendChild(row);
        }
    }
});



