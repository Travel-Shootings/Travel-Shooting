// 글 데이터 가져오기
const postId = window.location.pathname.split('/').pop();

$(document).ready(function () {
    loadPostData()
});

function loadPostData() {
    fetch('/api/posts/' + postId, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (!response.ok) {
                alert('해당 게시물은 존재하지 않습니다.');
                window.location.href = "/view/post"
            }
            return response.json();
        })
        .then(data => {
            let nickName = data.nickName;
            let livingPlace = data.region;
            let createdAt = data.createdAt;
            let title = data.title
            let contents = data.contents
            let commentList = data.commentList;
            let journeyList = data.journeyList; // journeyList 데이터 가져오기

            // 가져온 데이터를 HTML에 추가
            $('.topper mgn').text("닉네임: " + nickName);
            $('.topper mgn2').text("지역: " + livingPlace);
            $('.title temp').text(title); // .title 요소 안의 temp 클래스를 가진 요소에 title 값을 설정합니다.
            $('.post temp').text(contents); // .post 요소 안의 temp 클래스를 가진 요소에 contents 값을 설정합니다.


            if (commentList && commentList.length > 0) {
                // 댓글 데이터를 renderComments 함수로 전달하여 추가합니다.
                loadComments(commentList);
            }

            // 프론트엔드에서 journeyList 데이터를 반복하며 표시
            if (journeyList && journeyList.length > 0) {
                let journeyListHtml = '';
                journeyList.forEach((item, index) => {
                    // 각 항목에서 필요한 데이터 가져오기
                    const location = item.locations;
                    const budget = item.budget;
                    const members = item.members;
                    const startJourney = new Date(item.startJourney);
                    const endJourney = new Date(item.endJourney);
                    const placeAddress = item.placeAddress;

                    // 날짜를 YYYY년 MM월 DD일 HH시 MM분으로 표기하는 함수
                    const startFormatted = startJourney.toLocaleString('ko-KR', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: 'numeric',
                        minute: 'numeric',
                    });
                    const endFormatted = endJourney.toLocaleString('ko-KR', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: 'numeric',
                        minute: 'numeric',
                    });

                    // LocalDateTime 형식의 createdAt을 "nov 18, 2023" 형태로 변환
                    const months = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
                    const createdAtDate = new Date(createdAt); // LocalDateTime을 JavaScript Date 객체로 변환
                    const monthName = months[createdAtDate.getMonth()]; // 월 이름 가져오기
                    const formattedDate = `${monthName} ${createdAtDate.getDate()}, ${createdAtDate.getFullYear()}`;
                    $('.perm').text("작성일: " + formattedDate);


                    // HTML에 동적으로 추가
                    journeyListHtml += `<box> ${index + 1 + "번째 여행지"}
            <ciro>
                <circ></circ>
            </ciro>
            <nm>장소명: ${location}</nm>
            <nm>예산: ${budget}원</nm>
            <nm>인원: ${members}</nm>
            <nm>일정: ${startFormatted} ~ ${endFormatted}</nm>
            <nm>주소: ${placeAddress}</nm>
        </box>`;
                });
                $('.journeyList').html(journeyListHtml); // .journeyList 요소 안에 journeyList 데이터 추가
            }
        })
        .catch(error => {
            // 네트워크 오류나 서버 응답 오류 등을 처리
            alert(error.message);
            window.location.href = "/view/post"
        });
}


function checkAuthorizationCookie() {
    var cookies = document.cookie.split(";");

    for (var i = 0; i < cookies.length; i++) {
        var cookie = cookies[i].split('=');

        // "Authorization" 쿠키가 존재하는 경우 true 반환
        if (cookie[0] === "Authorization") {
            return true;
        }
    }
    // "Authorization" 쿠키가 존재하지 않는 경우 false 반환
    return false;
}

window.onload = function() {
    var comment_form = document.getElementById("comment-form");

    if (!checkAuthorizationCookie()) {
        comment_form.style.display = 'none';
    }
}

// 수정 기능
$(document).ready(function () {
    // id=editPost를 클릭했을 때의 동작을 정의합니다.
    $('#editPost').click(function () {
        if(!checkAuthorizationCookie()) {
            alert('로그인이 필요합니다.');
            return
        }

        // editPost 클릭 시 해당 URL로 이동합니다.
        window.location.href = '/view/post/edit/' + postId;
    });
});


const deletePostButton = document.getElementById("deletePost");
deletePostButton.addEventListener("click", deletePost);

function deletePost() {
    if(!checkAuthorizationCookie()) {
        alert('로그인이 필요합니다.');
        return
    }

    fetch('/api/posts/' + postId, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (response.status === 200) {
                // 게시글 삭제 성공
                alert("게시글 삭제 완료");
                window.location.href = "/view/post"
            } else if (response.status === 404) {
                // 해당 글이 존재하지 않음
                alert("해당 글은 존재하지 않습니다");
                window.location.href = "/view/post/" + postId
            } else {
                // 기타 오류 처리
                alert("기타 오류 발생");
                window.location.href = "/view/post/" + postId
            }
        })
        .catch(error => {
            // 네트워크 오류 등 예외 처리
            alert(error.message);
            window.location.href = "/view/post/" + postId
        });
}


function createCommentElement(comment) {
    const commentElement = document.createElement('li');
    commentElement.setAttribute('commentId', comment.id);
    commentElement.classList.add('comment');

    const authorElement = document.createElement('div');
    authorElement.textContent = `작성자: ${comment.nickName}`;
    authorElement.classList.add('comment-author');
    commentElement.appendChild(authorElement);

    // 댓글 내용과 버튼을 감싸는 div를 생성합니다.
    const commentContentWrapper = document.createElement('div');
    commentContentWrapper.classList.add('comment-content-wrapper');
    commentElement.appendChild(commentContentWrapper);

    const contentElement = document.createElement('div');
    contentElement.textContent = comment.content;
    contentElement.classList.add('comment-content');
    commentContentWrapper.appendChild(contentElement);

    const replyButton = document.createElement('button');
    replyButton.textContent = '대댓글';
    replyButton.classList.add('create-reply-button');
    replyButton.setAttribute('data-comment-id', comment.id);
    replyButton.setAttribute('data-comment-content', comment.content);
    commentContentWrapper.appendChild(replyButton);

    const editButton = document.createElement('button');
    editButton.textContent = '수정';
    editButton.classList.add('edit-comment-button');
    editButton.setAttribute('data-comment-id', comment.id);
    editButton.setAttribute('data-comment-content', comment.content);
    commentContentWrapper.appendChild(editButton);

    const deleteButton = document.createElement('button');
    deleteButton.textContent = '삭제';
    deleteButton.classList.add('delete-comment-button');
    deleteButton.setAttribute('data-comment-id', comment.id);
    commentContentWrapper.appendChild(deleteButton);

    // 생성된 댓글에 수정 폼 요소도 추가합니다.
    const editFormContainer = createEditFormElement(comment.id, comment.content);
    commentContentWrapper.appendChild(editFormContainer);

    // 대댓글을 불러오고 해당 댓글 하위에 추가합니다.
    if (comment.replyList && comment.replyList.length > 0) {
        comment.replyList.forEach(reply => {
            const replyElement = createReplyElement(reply);
            commentElement.appendChild(replyElement);
        });
    }

    return commentElement;
}

// 수정 폼 요소 생성 함수
function createEditFormElement(commentId, commentContent) {

    const editFormContainer = document.createElement('div');
    editFormContainer.id = `comment-edit-form-${commentId}`;
    editFormContainer.classList.add('comment-edit-form-container')
    editFormContainer.style.display = 'none'; // 초기에는 숨김 상태

    const textarea = document.createElement('textarea');
    textarea.id = `edit-comment-content-${commentId}`;
    textarea.classList.add('comment-edit-form-content')
    textarea.value = commentContent;

    const saveButton = document.createElement('button');
    saveButton.id = `save-comment-button-${commentId}`;
    saveButton.textContent = '수정';

    const cancelButton = document.createElement('button');
    cancelButton.id = `cancel-comment-button-${commentId}`;
    cancelButton.textContent = '취소';

    editFormContainer.appendChild(textarea);
    editFormContainer.appendChild(saveButton);
    editFormContainer.appendChild(cancelButton);

    return editFormContainer;
}

// 대댓글 DB에서 불러오기
function createReplyElement(reply) {
    const replyElement = document.createElement('li');
    replyElement.classList.add('reply-list');

    const replyWrapper = document.createElement('div');
    replyWrapper.classList.add('reply-wrapper');
    replyElement.appendChild(replyWrapper);

    const authorElement = document.createElement('div');
    authorElement.textContent = `작성자: ${reply.nickName}`;
    authorElement.classList.add('reply-author');
    replyWrapper.appendChild(authorElement);

    const replyContentWrapper = document.createElement('div');
    replyContentWrapper.classList.add('reply-content-wrapper');
    replyWrapper.appendChild(replyContentWrapper);

    const contentElement = document.createElement('div');
    contentElement.textContent = reply.content;
    replyContentWrapper.appendChild(contentElement);

    const editButton = document.createElement('button');
    editButton.textContent = '수정';
    editButton.classList.add('edit-reply-button');
    editButton.setAttribute('data-reply-id', reply.id);
    replyContentWrapper.appendChild(editButton);

    const deleteButton = document.createElement('button');
    deleteButton.textContent = '삭제';
    deleteButton.classList.add('delete-reply-button');
    deleteButton.setAttribute('data-reply-id', reply.id);
    replyContentWrapper.appendChild(deleteButton);

    return replyElement;
}

// 댓글 추가 반영
function loadComments(comments) {
    const commentList = document.getElementById('comment-list');
    commentList.innerHTML = '';

    for (const comment of comments) {
        const commentElement = createCommentElement(comment);
        commentList.appendChild(commentElement);
    }
}

// 댓글 생성
document.addEventListener('DOMContentLoaded', function () {
    const commentForm = document.getElementById('comment-form');

    commentForm.addEventListener('submit', function (e) {
        e.preventDefault();

        const commentContent = document.getElementById('comment-content').value;

        // AJAX를 사용하여 댓글 생성 요청을 보냅니다.
        fetch(`/api/comments/posts/` + postId, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({content: commentContent}),
        })
            .then(response => response.json())
            .then(data => {
                // 댓글 생성이 성공한 경우, 화면에 댓글을 추가합니다.
                const commentList = document.getElementById('comment-list');
                const commentElement = createCommentElement(data);
                commentList.appendChild(commentElement);

                // 댓글 입력 필드를 비웁니다.
                document.getElementById('comment-content').value = '';
            })
            .catch(error => {
                console.error('댓글 생성 에러:', error);
            });
    });
});

document.addEventListener('DOMContentLoaded', function () {
    const commentList = document.getElementById('comment-list');
    let editingCommentElement = null; // 현재 수정 중인 댓글을 추적

    commentList.addEventListener('click', function (e) {
        if(!checkAuthorizationCookie()) {
            alert('로그인 후 이용해주세요');
            return
        }

        const target = e.target;

        // 수정 버튼 클릭 시
        if (target.classList.contains('edit-comment-button')) {
            // 수정 버튼이 속한 댓글 요소를 찾습니다.
            const commentElement = target.closest('.comment');

            // 댓글 내용 요소를 찾아서 내용을 가져옵니다.
            const contentElement = commentElement.querySelector('.comment-content');
            const commentContent = contentElement.textContent;

            // 나머지 코드는 이전과 동일하게 진행합니다.
            const commentId = target.getAttribute('data-comment-id');

            // 수정 폼 아이디 동적 생성
            const editFormId = `comment-edit-form-${commentId}`;
            const editModal = document.getElementById(editFormId);

            // 수정 모달창을 열고 commentContent를 표시
            const editCommentContent = editModal.querySelector('textarea');
            editCommentContent.value = commentContent;

            // 현재 수정 중인 댓글 요소를 추적
            editingCommentElement = commentElement;

            // 모달창을 보이게 설정
            editModal.style.display = 'block';

            // 저장 버튼 클릭할 때
            function saveEditButtonClick() {
                const editedContent = editCommentContent.value;

                // AJAX를 사용하여 댓글 수정 요청을 보냅니다.
                fetch(`/api/comments/` + commentId, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({ content: editedContent }),
                })
                    .then(response => response.json())
                    .then(data => {
                        // 댓글 수정이 성공한 경우, 화면에서 댓글 내용을 업데이트합니다.
                        contentElement.textContent = editedContent;

                        // 수정 모달창 닫기
                        editModal.style.display = 'none';

                        // 수정 중인 댓글 요소 초기화
                        editingCommentElement = null;
                    })
                    .catch(error => {
                        console.error('댓글 수정 에러:', error);
                    });
            }

            const saveEditButton = document.getElementById(`save-comment-button-${commentId}`);
            saveEditButton.addEventListener('click', saveEditButtonClick);

            // 취소 버튼 클릭할 때
            function cancelEditButtonClick() {
                // 수정 모달창 닫기
                editModal.style.display = 'none';

                // 수정 중인 댓글 요소 초기화
                editingCommentElement = null;

                // 취소 버튼 클릭 이벤트 핸들러 제거
                cancelEditButton.removeEventListener('click', cancelEditButtonClick);

                // 저장 버튼 클릭 이벤트 핸들러 제거
                saveEditButton.removeEventListener('click', saveEditButtonClick);
            }

            const cancelEditButton = document.getElementById(`cancel-comment-button-${commentId}`);
            cancelEditButton.addEventListener('click', cancelEditButtonClick);
        }

        // 삭제 버튼 클릭 시
        if (target.classList.contains('delete-comment-button')) {
            const commentId = target.getAttribute('data-comment-id');

            if (confirm('댓글을 삭제하시겠습니까?')) {
                // AJAX를 사용하여 댓글 삭제 요청을 보냅니다.
                fetch(`/api/comments/` + commentId, {
                    method: 'DELETE',
                })
                    .then(response => response.json())
                    .then(data => {
                        // 댓글 삭제가 성공한 경우, 화면에서 댓글을 제거합니다.
                        const commentElement = target.closest('.comment');
                        commentElement.remove();
                    })
                    .catch(error => {
                        console.error('댓글 삭제 에러:', error);
                    });
            }
        }
    });
});



// 주소 검색의 이벤트
$('#address').on('keydown', function (e) {
    var keyCode = e.which;
    if (keyCode === 13) { // Enter Key
        searchAddressToCoordinate($('#address').val());
    }
});

// 전역 변수로 infoWindow 객체를 정의하고 초기화
var infoWindow = new naver.maps.InfoWindow({
    maxWidth: 300,
    backgroundColor: "#ffffff",
    borderColor: "#5347aa",
    borderWidth: 2,
    anchorSize: new naver.maps.Size(0, 0),
    anchorColor: "transparent",
    pixelOffset: new naver.maps.Point(20, -35)
})

// 전역 변수로 지도를 초기화
var map = new naver.maps.Map('map', {
    center: new naver.maps.LatLng(37.3595704, 127.105399),
    zoom: 10
});

// 전역 변수로 polyline 객체를 정의하고 초기화
var polyline = new naver.maps.Polyline({
    map: map,
    path: [],
    strokeColor: '#5347AA',
    strokeWeight: 2
});

// #submit 버튼 클릭 이벤트 핸들러
$('#submit').on('click', function (e) {
    e.preventDefault();
    searchAddressToCoordinate($('#address').val());
});

//검색한 주소의 정보를 insertAddress 함수로 넘겨준다.
function searchAddressToCoordinate(address) {
    naver.maps.Service.geocode({
        query: address
    }, function (status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('Something Wrong!');
        }
        if (response.v2.meta.totalCount === 0) {
            return alert('올바른 주소를 입력해주세요.');
        }
        var htmlAddresses = [],
            item = response.v2.addresses[0],
            point = new naver.maps.Point(item.x, item.y);
        if (item.roadAddress) {
            htmlAddresses.push('[도로명 주소] ' + item.roadAddress);
        }
        if (item.jibunAddress) {
            htmlAddresses.push('[지번 주소] ' + item.jibunAddress);
        }
        if (item.englishAddress) {
            htmlAddresses.push('[영문명 주소] ' + item.englishAddress);
        }

        // 정보 창을 열고 내용을 설정하여 지도에 표시
        infoWindow.setContent([
            '<div style="padding:10px;min-width:200px;line-height:150%;">',
            '<h5 style="margin-top:5px;">검색 주소 : ' + address + '</h5><br />',
            htmlAddresses.join('<br />'),
            '</div>'
        ].join('\n'));

        // 정보 창을 열 위치 설정
        infoWindow.open(map, point);

        // 지도 중심 이동
        map.setCenter(point);

        // 주소 정보를 표시하고 지도에 추가
        insertAddress(item.roadAddress, item.x, item.y);
    });
}

// 주소 정보를 표시하고 지도에 추가하는 함수
function insertAddress(address, latitude, longitude) {
    var mapList = "<tr>" +
        "    <td>" + address + "</td>" +
        "    <td>" + latitude + "</td>" +
        "    <td>" + longitude + "</td>" +
        "</tr>";

    $('#mapList').append(mapList);

    var newPosition = new naver.maps.LatLng(longitude, latitude);
    polyline.getPath().push(newPosition);

    var point = new naver.maps.LatLng(longitude, latitude);
    new naver.maps.Marker({
        map: map,
        position: point
    });
}

