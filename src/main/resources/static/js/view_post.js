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
                    $('.perm').text("작성일: "+ formattedDate);


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

// 수정 기능
$(document).ready(function () {
    // id=editPost를 클릭했을 때의 동작을 정의합니다.
    $('#editPost').click(function () {
        // editPost 클릭 시 해당 URL로 이동합니다.
        window.location.href = 'http://localhost:8080/view/post/edit/' + postId;
    });
});



const deletePostButton = document.getElementById("deletePost");
deletePostButton.addEventListener("click", deletePost);
    function deletePost() {
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

// 기존 댓글 DB에서 불러오기
function createCommentElement(comment) {
    const commentElement = document.createElement('li');
    commentElement.classList.add('comment');

    const authorElement = document.createElement('div');
    authorElement.textContent = `작성자: ${comment.nickName}`;
    authorElement.classList.add('comment-author');
    commentElement.appendChild(authorElement);

    // 댓글 내용과 버튼을 감싸는 div를 생성합니다.
    const commentContentWrapper = document.createElement('div');
    commentContentWrapper.classList.add('comment-content-wrapper');

    const contentElement = document.createElement('div');
    contentElement.textContent = comment.content;
    commentContentWrapper.appendChild(contentElement);

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

    commentElement.appendChild(commentContentWrapper);

    return commentElement;
}

//댓글 추가 반영
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
            body: JSON.stringify({ content: commentContent }),
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

    commentList.addEventListener('click', function (e) {
        const target = e.target;

        // 수정 버튼 클릭 시
        if (target.classList.contains('edit-comment-button')) {
            const commentId = target.getAttribute('data-comment-id');
            const commentContent = target.getAttribute('data-comment-content');

            // TODO: 수정 모달창을 열고 commentContent를 표시하세요.
            const editModal = document.getElementById('comment-edit-form');
            const editCommentContent = document.getElementById('edit-comment-content');
            editCommentContent.value = commentContent;
            editModal.style.display = 'block';

            // TODO: 수정 모달창에서 수정 내용을 입력받고, API를 호출하여 댓글을 수정하세요.
            const saveEditButton = document.getElementById('save-edit-button');
            saveEditButton.addEventListener('click', function () {
                const editedContent = editCommentContent.value;

                console.log(commentId);

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
                        const commentElement = target.closest('.comment');
                        const contentElement = commentElement.querySelector('.comment-content-wrapper div');
                        contentElement.textContent = editedContent;

                        //수정 후 페이지 새로고침
                        location.reload();
                        // 수정 모달창 닫기
                        // editModal.style.display = 'none';

                    })
                    .catch(error => {
                        console.error('댓글 수정 에러:', error);
                    });
            });

            // 취소 버튼을 클릭할 때 수정 모달 창을 닫습니다.
            const cancelEditButton = document.getElementById('cancel-edit-button');
            cancelEditButton.addEventListener('click', function () {
                editModal.style.display = 'none';
                location.reload();
            });
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



// 네이버 지도 API 함수
selectMapList();

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

        insertAddress(item.roadAddress, item.x, item.y);

    });
}

// 주소 검색의 이벤트
$('#address').on('keydown', function (e) {
    var keyCode = e.which;
    if (keyCode === 13) { // Enter Key
        searchAddressToCoordinate($('#address').val());
    }
});
$('#search-address').on('click', function (e) {
    e.preventDefault();
    searchAddressToCoordinate($('#address').val());
});
// naver.maps.Event.once(map, 'init_stylemap', initGeocoder)


//검색정보를 테이블로 작성해주고, 지도에 마커를 찍어준다.
function insertAddress(address, latitude, longitude) {
    var mapList = "";
    mapList += "<tr>"
    mapList += "	<td>" + address + "</td>"
    mapList += "	<td>" + latitude + "</td>"
    mapList += "	<td>" + longitude + "</td>"
    mapList += "</tr>"

    $('#mapList').append(mapList);

    var map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(longitude, latitude),
        zoom: 14
    });
    var marker = new naver.maps.Marker({
        map: map,
        position: new naver.maps.LatLng(longitude, latitude),
    });
}

//지도를 그려주는 함수
function selectMapList() {

    var map = new naver.maps.Map('map', {
        center: new naver.maps.LatLng(37.3595704, 127.105399),
        zoom: 10
    });
}


// 지도를 이동하게 해주는 함수
function moveMap(len, lat) {
    var mapOptions = {
        center: new naver.maps.LatLng(len, lat),
        zoom: 15,
        mapTypeControl: true
    };
    var map = new naver.maps.Map('map', mapOptions);
    var marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(len, lat),
        map: map
    });
}

