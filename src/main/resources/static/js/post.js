
$(document).ready(function () {
    loadAllPosts()
});

function loadAllPosts () {
    fetch('/api/posts', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => { // 테이블의 tbody 요소를 선택합니다.
            const tbody = document.querySelector('.board-table tbody');

            // 서버에서 받아온 데이터를 순회하면서 테이블 행을 추가합니다.
            data.forEach((post, index) => {
                // 새로운 행을 생성합니다.
                const row = document.createElement('tr');

                // 번호 열을 추가합니다.
                const numCell = document.createElement('td');
                numCell.textContent = index + 1; // 순서대로 번호 부여
                row.appendChild(numCell);

                // 제목 열을 추가합니다.
                const titleCell = document.createElement('th');
                titleCell.classList.add('th-title');
                const titleLink = document.createElement('a');
                titleLink.href = `/view/post/${post.id}`;
                titleLink.textContent = `${post.title}`;
                titleCell.appendChild(titleLink);
                row.appendChild(titleCell);

                // 닉네임 열을 추가합니다.
                const nickNameCell = document.createElement('td');
                nickNameCell.textContent = post.nickName;
                row.appendChild(nickNameCell);

                // 좋아요 열을 추가합니다.
                const likeCountsCell = document.createElement('td');
                likeCountsCell.textContent = post.likeCounts;
                row.appendChild(likeCountsCell);

                // 등록일 열을 추가합니다.
                const createdAtCell = document.createElement('td');
                const createdAt = new Date(post.createdAt);
                createdAtCell.textContent = createdAt.toLocaleDateString(); // 날짜 형식으로 표시
                row.appendChild(createdAtCell);

                // 테이블에 행을 추가합니다.
                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('데이터를 불러오는 중 오류가 발생했습니다:', error);
        });
}




