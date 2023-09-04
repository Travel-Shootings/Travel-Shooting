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


// 버튼 제어
var user_box = document.getElementById("user-box");
var post_box = document.getElementById("post-box");
var review_post_box = document.getElementById("review-post-box");

function openUserBox() {
    if (user_box.style.display === "block") {
        user_box.style.display = "none";
    } else {
        user_box.style.display = "block";

        post_box.style.display = "none";
        review_post_box.style.display = "none";
    }
}

function openPostBox() {

    if (post_box.style.display === "block") {
        post_box.style.display = "none";
    } else {
        post_box.style.display = "block";

        user_box.style.display = "none";
        review_post_box.style.display = "none";
    }
}

function openReviewPostBox() {
    if (review_post_box.style.display === "block") {
        review_post_box.style.display = "none";
    } else {
        review_post_box.style.display = "block";

        user_box.style.display = "none";
        post_box.style.display = "none";
    }
}

// ========================== 유저 정보 ==========================
// 유저 데이터 가져오기
fetch('/api/admin/users') // GET 요청으로 JSON 데이터 가져오기
    .then(response => response.json())
    .then(jsonData => {
        const listContainer = document.getElementById('user-box');
        console.log(jsonData);
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
                window.location.reload();
            }
        })
        .fail(function (request, status, error) {
            console.log(status)
            console.log(error)
            alert("유저 삭제에 실패했습니다.");
        });
}


// ========================== 계획 게시판 ==========================

// 계획 게시판 데이터 가져오기
fetch('/api/admin/posts') // GET 요청으로 JSON 데이터 가져오기
    .then(response => response.json())
    .then(jsonData => {
        const listContainer = document.getElementById('post-box');

        console.log(jsonData);
        jsonData.forEach(dataItem => {
            const listItem = document.createElement('div');
            listItem.className = 'user-item'; // CSS 클래스 추가

            const titleElement = document.createElement('div');
            titleElement.textContent = "Title : " + dataItem.title;
            listItem.appendChild(titleElement);

            const authorElement = document.createElement('div');
            authorElement.textContent = "Author : " + dataItem.nickName;
            listItem.appendChild(authorElement);

            const contentsElement = document.createElement('div');
            contentsElement.textContent = "Contents : " + dataItem.contents;
            listItem.appendChild(contentsElement);

            const likeElement = document.createElement('div');
            likeElement.textContent = "Likes : " + dataItem.likeCounts;
            listItem.appendChild(likeElement);

            const commentElement = document.createElement('div');
            commentElement.textContent = "Comment Count : " + dataItem.commentList.length;
            listItem.appendChild(commentElement);

            const editButton = document.createElement('button');
            editButton.textContent = '페이지 이동';
            editButton.onclick = () => {
                movePostPage(dataItem.id);
            };
            listItem.appendChild(editButton);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = '게시글 삭제';
            deleteButton.onclick = () => {
                const confirmResult = confirm('정말로 게시글을 삭제하시겠습니까?');
                if (confirmResult) {
                    deletePost(dataItem.id);
                }
            };
            listItem.appendChild(deleteButton);

            listContainer.appendChild(listItem); // 리스트 컨테이너에 추가
        });
    })
    .catch(error => console.error('Error:', error));

// 게시글 상세페이지 이동
function movePostPage(postId) {
    window.location.href = '/view/post/' + postId;
}

// 계획 게시글 삭제
function deletePost(postId) {
    $.ajax({
        type: "DELETE",
        url: "/api/admin/posts/" + postId,
        contentType: "application/json; charset=urf-8",
        dataType: "json"
    })
        .done(function (res) {
            console.log(res)
            console.log(res.statusCode)
            if (res.statusCode === 200) {
                alert(res.message)
                window.location.reload();
            }
        })
        .fail(function (request, status, error) {
            console.log(status)
            console.log(error)
            alert("게시글 삭제에 실패했습니다.");
        });
}

// ========================== 후기 게시판 ==========================
fetch('/api/admin/review-posts') // GET 요청으로 JSON 데이터 가져오기
    .then(response => response.json())
    .then(jsonData => {
        const listContainer = document.getElementById('review-post-box');

        console.log(jsonData);
        jsonData.forEach(dataItem => {
            const listItem = document.createElement('div');
            listItem.className = 'user-item'; // CSS 클래스 추가

            const titleElement = document.createElement('div');
            titleElement.textContent = "Title : " + dataItem.title;
            listItem.appendChild(titleElement);

            const authorElement = document.createElement('div');
            authorElement.textContent = "Author : " + dataItem.nickName;
            listItem.appendChild(authorElement);

            const contentsElement = document.createElement('div');
            contentsElement.textContent = "Contents : " + dataItem.content;
            listItem.appendChild(contentsElement);

            const likeElement = document.createElement('div');
            likeElement.textContent = "Likes : " + dataItem.likeCounts;
            listItem.appendChild(likeElement);

            const commentElement = document.createElement('div');
            commentElement.textContent = "Comment Count : " + dataItem.commentList.length;
            listItem.appendChild(commentElement);

            const moveButton = document.createElement('button');
            moveButton.textContent = '페이지 이동';
            moveButton.onclick = () => {
                moveReviewPostPage(dataItem.id);
            };
            listItem.appendChild(moveButton);

            const deleteButton = document.createElement('button');
            deleteButton.textContent = '게시글 삭제';

            deleteButton.onclick = () => {
                const confirmResult = confirm('정말로 게시글을 삭제하시겠습니까?');
                if (confirmResult) {
                    deleteReviewPost(dataItem.id);
                }
            };
            listItem.appendChild(deleteButton);

            listContainer.appendChild(listItem); // 리스트 컨테이너에 추가
        });
    })
    .catch(error => console.error('Error:', error));

// 후기 게시글 상세페이지 이동
function moveReviewPostPage(postId) {
    window.location.href = '/view/review-post/' + postId;
}

// 후기 게시글 삭제
function deleteReviewPost(postId) {
    $.ajax({
        type: "DELETE",
        url: "/api/admin/review-posts/" + postId,
        contentType: "application/json; charset=urf-8",
        dataType: "json"
    })
        .done(function (res) {
            console.log(res)
            console.log(res.statusCode)
            if (res.statusCode === 200) {
                alert(res.message)
                window.location.reload();
            }
        })
        .fail(function (request, status, error) {
            console.log(status)
            console.log(error)
            alert("게시글 삭제에 실패했습니다.");
        });
}
