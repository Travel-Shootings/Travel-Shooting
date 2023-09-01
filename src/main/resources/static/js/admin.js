// 총 유저, 게시글, 댓글 개수 가져오기
fetch('/api/admin/summary',  {
    method: 'GET',
    headers: {'Content-Type': 'application/json'}
}) // GET 요청으로 JSON 데이터 가져오기
    .then(response => response.json())
    .then(jsonData => {
        console.log(jsonData.allUsersCount);
        console.log(jsonData.allPostsCount);
        console.log(jsonData.allCommentsCount);

        var user_count = document.getElementById("user-count");
        var post_count = document.getElementById("post-count");
        var comment_count = document.getElementById("comment-count");

        user_count.textContent = jsonData.allUsersCount;
        post_count.textContent = jsonData.allPostsCount;
        comment_count.textContent = jsonData.allCommentsCount;
    })
    .catch(error => console.error('Error:', error));

// 유저 데이터 가져오기
fetch('/api/admin/users') // GET 요청으로 JSON 데이터 가져오기
    .then(response => response.json())
    .then(jsonData => {
        const listContainer = document.getElementById('user-box');

        jsonData.forEach(dataItem => {
            const listItem = document.createElement('div');
            listItem.className = 'user-item'; // CSS 클래스 추가

            const idElement = document.createElement('div');
            idElement.textContent = "User Id : " + dataItem.id;
            listItem.appendChild(idElement);

            const emailElement = document.createElement('div');
            emailElement.textContent = "Email : " + dataItem.email;
            listItem.appendChild(emailElement);

            const nicknameElement = document.createElement('div');
            nicknameElement.textContent = "Nickname : " + dataItem.nickname;
            listItem.appendChild(nicknameElement);

            const usernameElement = document.createElement('div');
            usernameElement.textContent = "Username : " + dataItem.username;
            listItem.appendChild(usernameElement);

            const regionElement = document.createElement('div');
            regionElement.textContent = "Region : " + dataItem.region;
            listItem.appendChild(regionElement);

            const roleElement = document.createElement('div');
            roleElement.textContent = "Role : " + dataItem.role;
            listItem.appendChild(roleElement);

            const editButton = document.createElement('button');
            editButton.textContent = '정보 수정';
            editButton.onclick = () => {
                openUserEditBox(dataItem.id);
            };
            listItem.appendChild(editButton);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = '강제 탈퇴';
            deleteButton.onclick = () => {
                const confirmResult = confirm('정말로 유저를 탈퇴시키시겠습니까?');
                if (confirmResult) {
                    deleteUser(dataItem.id);
                }
            };
            listItem.appendChild(deleteButton);

            listContainer.appendChild(listItem); // 리스트 컨테이너에 추가
        });
    })
    .catch(error => console.error('Error:', error));

function openUserBox() {
    var user_box = document.getElementById("user-box");

    if (user_box.style.display === "block") {
        user_box.style.display = "none";
    } else {
        user_box.style.display = "block";
    }
}

// 유저 정보 수정 팝업 열기
function openUserEditBox(userId) {
    const popupWidth = 500; // 팝업 창 너비
    const popupHeight = 500; // 팝업 창 높이

    const screenWidth = window.innerWidth; // 브라우저 창 너비
    const screenHeight = window.innerHeight; // 브라우저 창 높이

    const left = (screenWidth - popupWidth) / 2; // 가로 가운데 정렬
    const top = (screenHeight - popupHeight) / 2; // 세로 가운데 정렬

    // /view/user/editPassword
    const popupUrl = `/view/admin/user-edit?userId=${userId}`;
    const popupFeatures = `width=${popupWidth},height=${popupHeight},left=${left},top=${top}`;

    window.open(popupUrl, '유저 정보 수정', popupFeatures);
}

// 유저 데이터 삭제
function deleteUser(userId) {
    $.ajax({
        type: "DELETE",
        url: "/api/admin/user/" + userId,
        contentType: "application/json; charset=urf-8",
        dataType: "json"
    })
        .done(function (res) {
            console.log(res)
            console.log(res.statusCode)
            if (res.statusCode === 200) {
                alert(res.message)
                window.location.reload(); // 부모 창 새로고침
            } else if (res.statusCode === 400) {
                alert(res.message)
            }
        })
        .fail(function (request, status, error) {
            console.log(status)
            console.log(error)
            alert("유저 삭제에 실패했습니다.");
        });

}