$(document).ready(function () {
    let currentPage = 1; // 초기 페이지 번호
    const pageSize = 6; // 페이지당 아이템 수

    // 페이지 로딩 시 데이터 로드
    loadPosts(currentPage, pageSize);

    // 이전 페이지 버튼 클릭 시
    $('#prevPage').click(function () {
        if (currentPage > 1) {
            currentPage--;
            loadPosts(currentPage, pageSize); // 페이지 번호와 페이지 크기를 전달
        }
    });

// 다음 페이지 버튼 클릭 시
    $('#nextPage').click(function () {
        const totalPosts = parseInt($('#totalPosts').text());
        const totalPages = Math.ceil(totalPosts / pageSize);
        if (currentPage < totalPages) {
            currentPage++;
            loadPosts(currentPage, pageSize); // 페이지 번호와 페이지 크기를 전달
        }
    });
});

function loadPosts(pageNumber, pageSize) {
    fetch(`/api/posts/six?page=${pageNumber}&size=${pageSize}`, {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => response.json())
        .then(data => {
            updatePostList(data);
            $('#currentPage').text(pageNumber);
            $('#totalPages').text(data.totalPages); // 여기서 문제 발생
            updatePageNumbers(pageNumber, data.totalPages); // 페이지 번호 목록 업데이트 -> 여기서 넘어갈때 2번째 파라미터 undefined
        })
        .catch(error => {
            console.error('데이터를 불러오는 중 오류가 발생했습니다:', error);
        });
}

function updatePostList(data) {
    const tbody = $('.board-table tbody');
    tbody.empty(); // 기존 목록 제거

    data.forEach((post, index) => {
        const row = $('<tr>');

        // 번호 열을 추가합니다.
        const numCell = $('<td>');
        numCell.text(index + 1); // 순서대로 번호 부여
        row.append(numCell);

        // 제목 열을 추가합니다.
        const titleCell = $('<th>');
        titleCell.addClass('th-title');
        const titleLink = $('<a>');
        titleLink.attr('href', `/view/post/${post.id}`);
        titleLink.text(post.title);
        titleCell.append(titleLink);
        row.append(titleCell);

        // 닉네임 열을 추가합니다.
        const nickNameCell = $('<td>');
        nickNameCell.text(post.nickName);
        row.append(nickNameCell);

        // 좋아요 열을 추가합니다.
        const likeCountsCell = $('<td>');
        likeCountsCell.text(post.likeCounts);
        row.append(likeCountsCell);

        // 등록일 열을 추가합니다.
        const createdAtCell = $('<td>');
        const createdAt = new Date(post.createdAt);
        createdAtCell.text(createdAt.toLocaleDateString()); // 날짜 형식으로 표시
        row.append(createdAtCell);

        // 테이블에 행을 추가합니다.
        tbody.append(row);
    });
}

// 페이지 번호 목록을 업데이트하는 함수
function updatePageNumbers(currentPage, totalPages) {
    const pageNumbersContainer = $('#pageNumbers');
    pageNumbersContainer.empty(); // 이전 페이지 번호 목록 제거

    const pageCountToShow = 5; // 표시할 페이지 번호 개수
    const startPage = Math.max(currentPage - Math.floor(pageCountToShow / 2), 1);
    const endPage = Math.min(startPage + pageCountToShow - 1, 20);

    for (let i = startPage; i <= endPage; i++) {
        const pageNumberButton = $('<button>');
        pageNumberButton.text(i);
        pageNumberButton.click(function () {
            loadPosts(i, 6);
        });

        // 현재 페이지와 같은 페이지는 활성화 스타일을 적용
        if (i === currentPage) {
            pageNumberButton.addClass('active');
        }

        pageNumbersContainer.append(pageNumberButton);
    }
}

