// 글 데이터 가져오기
const postId = window.location.pathname.split('/').pop();

$(document).ready(function () {
    loadPostData();
});

function loadPostData() {
    fetch('/api/posts/' + postId, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorResponse => {
                    alert(errorResponse.message); // 예외 메세지를 표시
                    window.location.href = "/view/post";
                });
            }
            return response.json();
        })
        .then(async data => {
            // 좋아요 버튼 초기화
            let nickName = data.nickName;
            let livingPlace = data.region;
            let likeCounts = data.likeCounts;
            let createdAt = data.createdAt;
            let title = data.title;
            let contents = data.contents;
            let commentList = data.commentList;
            let journeyList = data.journeyList; // journeyList 데이터 가져오기

            // 가져온 데이터를 HTML에 추가
            $('.topper mgn').text("닉네임: " + nickName);
            $('.topper mgn2').text("지역: " + livingPlace);
            $('.likeCount').text(`좋아요: ${likeCounts}`);
            initializeLikeButton(postId, likeCounts);
            $('.title temp').text(title); // .title 요소 안의 temp 클래스를 가진 요소에 title 값을 설정합니다.
            $('.post temp').text(contents); // .post 요소 안의 temp 클래스를 가진 요소에 contents 값을 설정합니다.

            if (commentList && commentList.length > 0) {
                // 댓글 데이터를 renderComments 함수로 전달하여 추가합니다.
                loadComments(commentList);
            }

            // 프론트엔드에서 journeyList 데이터를 반복하며 표시
            if (journeyList && journeyList.length > 0) {
                let journeyListHtml = '';
                let journeyListCount = 1; // journeyListCount를 1로 초기화

                // 배열의 forEach 대신 for...of 루프를 사용하여 비동기 작업 순서를 보장합니다.
                for (const item of journeyList) {
                    // 각 항목에서 필요한 데이터 가져오기
                    const location = item.locations;
                    const budget = item.budget;
                    const members = item.members;
                    const startJourney = new Date(item.startJourney);
                    const endJourney = new Date(item.endJourney);
                    const placeAddress = item.placeAddress;

                    try {
                        // DB에서 받아온 주소 값을 비동기로 지도에 찍기
                        $("#address").val(placeAddress);
                        await searchAddressToCoordinate($('#address').val());

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
                        const createdAtDate = new Date(data.createdAt); // LocalDateTime을 JavaScript Date 객체로 변환
                        const monthName = months[createdAtDate.getMonth()]; // 월 이름 가져오기
                        const formattedDate = `${monthName} ${createdAtDate.getDate()}, ${createdAtDate.getFullYear()}`;
                        $('.perm').text("작성일: " + formattedDate);

                        // HTML에 동적으로 추가
                        journeyListHtml += `<box> ${`${journeyListCount} 번째 여행지`}
            
            <nm>장소명: ${location}</nm>
            <nm>예산: ${budget}원</nm>
            <nm>인원: ${members}</nm>
            <nm>일정: ${startFormatted} ~ ${endFormatted}</nm>
            <nm>주소: ${placeAddress}</nm>
        </box>`;
                        journeyListCount++;
                    } catch (error) {
                        console.error('Error processing address:', error);
                    }
                }

                // 비동기 작업이 모두 완료된 후에 HTML을 업데이트합니다.
                $('.journeyList').html(journeyListHtml);
            }
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

window.onload = function () {
    var comment_form = document.getElementById("comment-form");

    if (!checkAuthorizationCookie()) {
        comment_form.style.display = 'none';
    }
}

// 수정 기능
$(document).ready(function () {
    // id=editPost를 클릭했을 때의 동작을 정의합니다.
    $('#editPost').click(function () {
        if (!checkAuthorizationCookie()) {
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
    const confirmation = confirm('게시글을 삭제하시겠습니까?');

    if (confirmation) {
        if (!checkAuthorizationCookie()) {
            alert('작성자만 게시글을 삭제할 수 있습니다.');
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
                    alert("작성자만 게시글을 삭제할 수 있습니다.");
                    window.location.href = "/view/post/" + postId
                }
            })
            .catch(error => {
                // 네트워크 오류 등 예외 처리
                alert(error.message);
                window.location.href = "/view/post/" + postId
            });
    }
}

// 댓글 요소 생성 함수
function createCommentElement(comment) {
    const commentElement = document.createElement('li');
    commentElement.setAttribute('commentId', comment.id);
    commentElement.classList.add('comment');

    const authorElement = document.createElement('div');
    authorElement.textContent = `${comment.nickName}`;
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
    replyButton.classList.add('add-reply-button');
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

    // 대댓글을 누르면 생성할 수 있는 폼 요소도 추가합니다.
    const createReplyFormContainer = createReplyFormElement(comment.id);
    commentContentWrapper.appendChild(createReplyFormContainer);

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

// 댓글의 대댓글 생성 함수
function createReplyFormElement(commentId) {
    const replyFormContainer = document.createElement('div');
    replyFormContainer.id = `reply-form-container-${commentId}`;
    replyFormContainer.classList.add('reply-form-container');
    replyFormContainer.style.display = 'none'; // 초기에는 숨김 상태

    const replyContentInput = document.createElement('textarea');
    replyContentInput.classList.add('reply-content-input');
    replyContentInput.placeholder = '대댓글을 작성하세요';
    replyFormContainer.appendChild(replyContentInput);

    const createReplyButton = document.createElement('button');
    createReplyButton.textContent = '대댓글 작성';
    createReplyButton.classList.add('create-reply-button');
    replyFormContainer.appendChild(createReplyButton);

    const cancelReplyButton = document.createElement('button');
    cancelReplyButton.textContent = '취소';
    cancelReplyButton.classList.add('cancel-reply-button');
    replyFormContainer.appendChild(cancelReplyButton);

    // 대댓글 작성 버튼 클릭할 때
    createReplyButton.addEventListener('click', function (e) {
        e.preventDefault();
        const replyContent = replyContentInput.value;

        // AJAX를 사용하여 대댓글 생성 요청을 보냅니다.
        fetch(`/api/replies/${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({content: replyContent}),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('대댓글 생성 실패.');
                }
                return response.json();
            })
            .then(data => {
                // 대댓글 생성이 성공한 경우, 화면에 대댓글을 추가합니다.
                addReplyToComment(commentId, data);

                // 대댓글 입력 폼을 숨깁니다.
                replyFormContainer.style.display = 'none';

                // 입력 내용을 지웁니다.
                replyContentInput.value = '';
            })
            .catch(error => {
                console.error('대댓글 생성 에러:', error);
            });
    });

    // 취소 버튼 클릭할 때
    cancelReplyButton.addEventListener('click', function () {
        // 대댓글 입력 폼을 숨깁니다.
        replyFormContainer.style.display = 'none';

        // 입력 내용을 지웁니다.
        replyContentInput.value = '';
    });

    return replyFormContainer;
}

// 대댓글 수정 폼 요소 생성 함수
function createEditReplyFormElement(replyId, replyContent) {
    const editFormContainer = document.createElement('div');
    editFormContainer.id = `reply-edit-form-${replyId}`;
    editFormContainer.classList.add('reply-edit-form-container');
    editFormContainer.style.display = 'none'; // 초기에는 숨김 상태

    const textarea = document.createElement('textarea');
    textarea.id = `edit-reply-content-${replyId}`;
    textarea.classList.add('reply-edit-form-content');
    textarea.value = replyContent;

    const saveButton = document.createElement('button');
    saveButton.id = `save-reply-button-${replyId}`;
    saveButton.classList.add('save-reply-button');
    saveButton.textContent = '대댓글 수정';

    const cancelButton = document.createElement('button');
    cancelButton.id = `cancel-reply-button-${replyId}`;
    cancelButton.classList.add('cancel-reply-button');
    cancelButton.textContent = '취소';

    editFormContainer.appendChild(textarea);
    editFormContainer.appendChild(saveButton);
    editFormContainer.appendChild(cancelButton);

    return editFormContainer;
}

// 대댓글 수정 버튼 클릭 시
document.addEventListener('click', function (e) {
    const target = e.target;

    if (target.classList.contains('edit-reply-button')) {
        const replyId = target.getAttribute('data-reply-id');
        const editModal = document.getElementById(`reply-edit-form-${replyId}`);
        const editReplyContent = editModal.querySelector('textarea');
        // 대댓글 내용을 가져와서 수정 폼의 textarea에 설정합니다.
        editReplyContent.value = target.getAttribute('data-reply-content');

        // 저장 버튼 클릭 이벤트 리스너 등록
        const saveEditReplyButton = editModal.querySelector(`#save-reply-button-${replyId}`);
        saveEditReplyButton.addEventListener('click', function () {
            const editedContent = editReplyContent.value;

            // AJAX를 사용하여 대댓글 수정 요청을 보냅니다.
            fetch(`/api/replies/` + replyId, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({content: editedContent}),
            })
                .then(response => response.json())
                .then(data => {
                    // 대댓글 수정 모달창 닫기
                    editModal.style.display = 'none';

                    // 서버 응답에서 수정된 내용을 받아옵니다.
                    const updatedContent = data.content;

                    // 수정된 내용을 대댓글 요소에 반영
                    const replyElement = document.querySelector(`li[data-reply-id="${replyId}"]`);
                    if (replyElement) {
                        const replyContentElement = replyElement.querySelector('.edit-reply-content');
                        if (replyContentElement) {
                            replyContentElement.textContent = updatedContent;
                        }
                    }
                    alert("대댓글 수정 완료");
                    location.reload() // 해당 새로고침은 추후 수정
                })
                .catch(error => {
                    console.error('대댓글 수정 에러:', error);
                });
        });

        // 취소 버튼 클릭 이벤트 리스너 등록
        const cancelEditReplyButton = editModal.querySelector(`#cancel-reply-button-${replyId}`);
        cancelEditReplyButton.addEventListener('click', function () {
            // 대댓글 수정 모달창 닫기
            editModal.style.display = 'none';
        });

        // 대댓글 수정 폼을 보이게 설정
        editModal.style.display = 'block';
    }
});

// 대댓글 삭제 기능
document.addEventListener('click', function (e) {
    const target = e.target;

    // 대댓글 삭제 버튼 클릭 시
    if (target.classList.contains('delete-reply-button')) {
        const replyId = target.getAttribute('data-reply-id');

        if (confirm('대댓글을 삭제하시겠습니까?')) {
            // AJAX를 사용하여 대댓글 삭제 요청을 보냅니다.
            fetch(`/api/replies/` + replyId, {
                method: 'DELETE',
            })
                .then(response => response.json())
                .then(data => {
                    // 대댓글 삭제가 성공한 경우, 화면에서 대댓글을 제거합니다.
                    const replyElement = target.closest('.reply-list');
                    replyElement.remove();
                })
                .catch(error => {
                    console.error('대댓글 삭제 에러:', error);
                });
        }
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const commentList = document.getElementById('comment-list');

    // 댓글 목록에서 클릭 이벤트 처리
    commentList.addEventListener('click', function (e) {
        const target = e.target;

        // 대댓글 버튼 클릭 시
        if (target.classList.contains('add-reply-button')) {
            const commentElement = target.closest('.comment'); // 대댓글 버튼이 있는 댓글 요소

            if (commentElement) {
                const commentId = target.getAttribute('data-comment-id');
                const replyFormContainer = commentElement.querySelector(`#reply-form-container-${commentId}`); // 수정된 부분

                // 현재 display 상태를 확인하고 반대로 토글
                replyFormContainer.style.display = replyFormContainer.style.display === 'none' ? 'block' : 'none';
            }
        }
    });
});

// 대댓글 DB에서 불러오기
function createReplyElement(reply) {
    const replyElement = document.createElement('li');
    replyElement.classList.add('reply-list');

    const replyWrapper = document.createElement('div');
    replyWrapper.classList.add('reply-wrapper');
    replyElement.appendChild(replyWrapper);

    const authorElement = document.createElement('div');
    authorElement.textContent = `${reply.nickName}`;
    authorElement.classList.add('reply-author');
    replyWrapper.appendChild(authorElement);

    const replyContentWrapper = document.createElement('div');
    replyContentWrapper.classList.add('reply-content-wrapper');
    replyWrapper.appendChild(replyContentWrapper);

    const contentElement = document.createElement('div');
    contentElement.classList.add('edit-reply-content');
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

    const editReplyContainer = createEditReplyFormElement(reply.id, reply.content)
    replyContentWrapper.appendChild(editReplyContainer);

    return replyElement;
}


// 대댓글 추가 함수
function addReplyToComment(commentId, reply) {
    const commentElement = document.querySelector(`li[commentId="${commentId}"]`);
    if (commentElement) {
        const replyElement = createReplyElement(reply);
        commentElement.appendChild(replyElement);
    }
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

// 댓글의 수정과 삭제 기능
document.addEventListener('DOMContentLoaded', function () {
    const commentList = document.getElementById('comment-list');
    let editingCommentElement = null; // 현재 수정 중인 댓글을 추적

    commentList.addEventListener('click', function (e) {
        if (!checkAuthorizationCookie()) {
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
                    body: JSON.stringify({content: editedContent}),
                })
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(errorResponse => {
                                alert(errorResponse.message);
                                window.location.reload()
                            });
                        }
                        return response.json();
                    })
                    .then(data => {
                        // 댓글 수정이 성공한 경우, 화면에서 댓글 내용을 업데이트합니다.
                        contentElement.textContent = editedContent;

                        // 수정 모달창 닫기
                        editModal.style.display = 'none';

                        // 수정 중인 댓글 요소 초기화
                        editingCommentElement = null;
                    })
                    .catch(error => {
                        alert(error.message)
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
                    .then(response => {
                        if (!response.ok) {
                            return response.json().then(errorResponse => {
                                alert(errorResponse.message);
                                window.location.reload()
                            });
                        }
                        return response.json();
                    })
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

// 좋아요 버튼 기능
function initializeLikeButton(postId, initialLikeCount) {
    const likeButton = document.getElementById('likePost'); // 좋아요 버튼 요소 가져오기

    // 좋아요 버튼 클릭 시
    likeButton.addEventListener('click', async () => {
        try {
            const cancelResponse = await fetch(`/api/posts/${postId}/like`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            if (cancelResponse.ok) {
                alert('좋아요가 취소되었습니다.');
                initialLikeCount--; // 좋아요 수를 1 감소
                updateLikeCount(initialLikeCount); // 좋아요 수를 업데이트
            } else if (!cancelResponse.ok) {
                // 아직 좋아요를 누르지 않은 상태이므로 좋아요 요청을 보냅니다.
                const likeResponse = await fetch(`/api/posts/${postId}/like`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (likeResponse.ok) {
                    alert('좋아요가 등록되었습니다.');
                    initialLikeCount++; // 좋아요 수를 1 증가
                    updateLikeCount(initialLikeCount); // 좋아요 수를 업데이트
                } else if (!likeResponse.ok) {
                    alert('자신의 글에는 좋아요를 할 수 없습니다.')
                    console.error('Error adding like:', likeResponse.statusText);
                }
            } else {
                alert(cancelResponse.error)
            }
        } catch (error) {
            alert(error.message)
            console.error('Error handling like:', error);
        }
    });
}

// 좋아요 수 업데이트 함수
function updateLikeCount(likeCounts) {
    // 좋아요 수를 HTML 요소에 업데이트
    document.querySelector('.likeCount').textContent = `좋아요: ${likeCounts}`;
}


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

var center = new naver.maps.LatLng(37.3595704, 127.105399);

// 전역 변수로 지도를 초기화
var map = new naver.maps.Map('map', {
    center: center,
    zoom: 8
});

// 전역 변수로 polyline 객체를 정의하고 초기화
var polyline = new naver.maps.Polyline({
    map: map,
    path: [],
    strokeColor: '#5347AA',
    strokeWeight: 2
});

//검색한 주소의 정보를 insertAddress 함수로 넘겨준다.
function searchAddressToCoordinate(address) {
    return new Promise((resolve, reject) => {
        naver.maps.Service.geocode({
            query: address
        }, function (status, response) {
            if (status === naver.maps.Service.Status.ERROR) {
                reject('네이버 지도 연결 오류');
                return;
            }
            if (response.v2.meta.totalCount === 0) {
                reject('올바른 주소를 입력해주세요.');
                return;
            }

            var item = response.v2.addresses[0];
            var latitude = item.x;
            var longitude = item.y;
            insertAddress(item.roadAddress, latitude, longitude);

            resolve({
                address: address,
                latitude: latitude,
                longitude: longitude
            });
        });
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

    center = new naver.maps.LatLng(longitude, latitude); // center 좌표를 업데이트
    map.setCenter(center); // 지도의 중심 업데이트

    var point = new naver.maps.LatLng(longitude, latitude);
    new naver.maps.Marker({
        map: map,
        position: point
    });
}

