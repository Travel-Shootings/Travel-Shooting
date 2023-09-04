const urlParams = new URLSearchParams(window.location.search);
const postTitleElement = document.getElementById('post-title');
const postContentElement = document.getElementById('post-content');
const imageContainer = document.getElementById('image-container'); // 이미지를 표시할 컨테이너
const deleteButton = document.getElementById('delete-button'); // 삭제 버튼
const editButton = document.getElementById('edit-button');
// 좋아요 버튼 요소를 가져옵니다.
const likeButton = document.getElementById('like-button');

// URL 경로에서 reviewPostId 추출
const reviewPostId = window.location.pathname.split('/').pop();

// 서버에서 게시물 데이터 조회
async function fetchPostData() {
    try {
        const response = await fetch(`/api/review-posts/${reviewPostId}`);
        const data = await response.json();

        return data;
    } catch (error) {
        console.error('Error fetching post data:', error);
        return null;
    }
}

// 이미지 데이터를 화면에 표시하는 함수
function renderImages(imageUrls) {
    for (const imageUrl of imageUrls) {
        const imageElement = document.createElement('img');
        imageElement.src = imageUrl;
        imageElement.classList.add('post-image'); // 이미지 스타일을 위한 클래스 추가
        imageContainer.appendChild(imageElement);
    }
}

// 페이지 로드 시 게시물 데이터 조회 및 화면에 표시
async function init() {
    const postData = await fetchPostData();

    if (postData) {
        postTitleElement.textContent = postData.title;
        postContentElement.textContent = postData.content;

        // 이미지 URL 정보를 이용해 이미지를 화면에 표시
        renderImages(postData.imageUrls);

        // 좋아요 수를 가져와서 UI에 표시
        const likeCountElement = document.getElementById('like-count');
        likeCountElement.textContent = postData.likeCounts;

        // 버튼 업데이트
        updateLikeButton(postData.isLiked); // postData에서 좋아요 상태를 가져와 업데이트

        // 삭제 버튼 이벤트 핸들러 등록
        deleteButton.addEventListener('click', deleteReviewPost);

        // 수정 버튼 이벤트 핸들러 등록
        editButton.addEventListener('click', () => {
            window.location.href = `/view/review-post/update/${reviewPostId}`;
        });

    } else {
        postTitleElement.textContent = 'Error';
        postContentElement.textContent = 'Failed to load post data.';
    }
}

// 후기 게시글 삭제 요청 함수
async function deleteReviewPost() {
    const confirmation = confirm('게시글을 삭제하시겠습니까?');

    if (confirmation) {
        try {
            const response = await fetch(`/api/review-posts/${reviewPostId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const data = await response.json();
                if (data.status === 200) {
                    alert('게시글이 삭제되었습니다.');
                } else {
                    alert(data.message);
                }
                window.location.href = '/view/review-post';
            } else {
                console.error('Error deleting review post:', response.statusText);
            }
        } catch (error) {
            console.error('Error deleting review post:', error);
        }
    }
}

// 좋아요 버튼의 텍스트를 업데이트하는 함수
function updateLikeButton(isLiked) {
    if (isLiked) {
        likeButton.textContent = '좋아요 취소';
    } else {
        likeButton.textContent = '좋아요';
    }
}


// 좋아요 버튼 클릭 이벤트를 처리합니다.
likeButton.addEventListener('click', async () => {
    try {
        const response = await fetch(`/api/review-posts/like/${reviewPostId}`);
        const data = await response.json();

        if (data.message === '이미 좋아요를 누른 상태입니다.') {
            // 이미 좋아요를 누른 상태이므로 좋아요 취소 요청을 보냅니다.
            const cancelResponse = await fetch(`/api/review-posts/like/${reviewPostId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (cancelResponse.ok) {
                const cancelData = await cancelResponse.json();
                if (cancelData.status === 200) {
                    alert('좋아요가 취소되었습니다.');
                } else {
                    alert(cancelData.message);
                }
                window.location.reload();
            } else {
                console.error('Error canceling like:', cancelResponse.statusText);
            }
        } else {
            // 아직 좋아요를 누르지 않은 상태이므로 좋아요 요청을 보냅니다.
            const likeResponse = await fetch(`/api/review-posts/like/${reviewPostId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (likeResponse.ok) {
                const likeData = await likeResponse.json();
                if (likeData.status === 200) {
                    alert('좋아요가 등록되었습니다.');
                } else {
                    alert(likeData.message);
                }
                window.location.reload();
            } else {
                console.error('Error adding like:', likeResponse.statusText);
            }
        }
    } catch (error) {
        console.error('Error handling like:', error);
    }
});


// 리뷰 포스트 댓글 생성 API 엔드포인트
const apiUrl = `/api/comments/reviewPost/${reviewPostId}`;
const commentForm = document.getElementById('comment-form');
const commentContent = document.getElementById('comment-content');

commentForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const content = commentContent.value;
    if (!content) {
        alert('댓글 내용을 입력하세요.');
        return;
    }

    // 요청 데이터 객체 생성
    const requestData = {
        content: content // 댓글 내용
    };

    try {
        // API에 POST 요청 보내기
        const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const responseData = await response.json();
            if (responseData) {
                // 성공적으로 댓글이 생성되었을 때 처리
                alert('댓글이 성공적으로 생성되었습니다.');
                location.reload();
            } else {
                console.error('댓글 생성 실패');
            }
        } else {
            console.error('댓글 생성 요청 실패:', response.statusText);
        }
    } catch (error) {
        console.error('댓글 생성 요청 에러:', error);
    }
});

const commentsApiUrl = `/api/comments/reviewPost/${reviewPostId}`;

function renderComments(comments) {
    const commentList = document.getElementById('comment-list');
    commentList.innerHTML = '';

    for (const comment of comments) {
        const commentElement = document.createElement('li');
        commentElement.classList.add('comment');

        const authorElement = document.createElement('div');
        authorElement.textContent = `작성자: ${comment.nickName}`;
        commentElement.appendChild(authorElement);

        const contentElement = document.createElement('div');
        contentElement.textContent = comment.content;
        commentElement.appendChild(contentElement);

        const editButton = document.createElement('button');
        editButton.textContent = '수정';
        editButton.classList.add('edit-comment-button');
        editButton.setAttribute('data-comment-id', comment.id);
        editButton.setAttribute('data-comment-content', comment.content);
        commentElement.appendChild(editButton);

        const deleteButton = document.createElement('button');
        deleteButton.textContent = '삭제'; // 삭제 버튼 추가
        deleteButton.classList.add('delete-comment-button'); // 삭제 버튼 클래스 추가
        deleteButton.setAttribute('data-comment-id', comment.id); // 삭제 버튼에 댓글 ID 속성 추가
        commentElement.appendChild(deleteButton);

        commentList.appendChild(commentElement);
    }
}

async function fetchComments() {
    try {
        const response = await fetch(commentsApiUrl);
        const comments = await response.json();

        renderComments(comments);

        const commentsContainer = document.getElementById('comments-container');
        commentsContainer.scrollTop = commentsContainer.scrollHeight;
    } catch (error) {
        console.error('Error fetching comments:', error);
    }
}

fetchComments();

document.getElementById('comment-list').addEventListener('click', (e) => {
    if (e.target.classList.contains('edit-comment-button')) {
        const commentId = e.target.getAttribute('data-comment-id');
        const commentContent = e.target.getAttribute('data-comment-content');
        openEditForm(commentId, commentContent);
    }

    if (e.target.classList.contains('delete-comment-button')) {
        const commentId = e.target.getAttribute('data-comment-id');
        deleteComment(commentId);
    }
});

function openEditForm(commentId, content) {
    const commentEditForm = document.getElementById('comment-edit-form');
    commentEditForm.style.display = 'block';

    const editCommentContent = document.getElementById('edit-comment-content');
    editCommentContent.value = '';

    const saveEditButton = document.getElementById('save-edit-button');
    saveEditButton.addEventListener('click', () => {
        saveEditedComment(commentId);
    });

    const cancelEditButton = document.getElementById('cancel-edit-button');
    cancelEditButton.addEventListener('click', cancelEditComment);
}

async function saveEditedComment(commentId) {
    const editCommentContent = document.getElementById('edit-comment-content').value;

    if (!editCommentContent) {
        alert('댓글 내용을 입력하세요.');
        return;
    }

    const requestData = {
        content: editCommentContent
    };

    try {
        const response = await fetch(`/api/comments/${commentId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const responseData = await response.json();
            if (responseData.status === 200) {
                alert('댓글이 수정되었습니다.');

            } else {
                alert(responseData.message);
            }

        } else {
            console.error('Error updating comment:', response.statusText);
        }

        fetchComments();
        location.reload(); // 페이지 리로드

    } catch (error) {
        console.error('Error updating comment:', error);
    }
}

function cancelEditComment() {
    closeEditForm();
}

// 댓글 삭제 함수
async function deleteComment(commentId) {
    const confirmation = confirm('댓글을 삭제하시겠습니까?');

    if (confirmation) {
        try {
            const response = await fetch(`/api/comments/${commentId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const data = await response.json();
                if (data.status === 200) {
                    alert('댓글이 삭제되었습니다.');

                } else {
                    alert(data.message);
                }
            } else {
                console.error('Error deleting comment:', response.statusText);
            }

            fetchComments();
            location.reload(); // 페이지 리로드
        } catch (error) {
            console.error('Error deleting comment:', error);
        }
    }
}

init();
