const postTitleElement = document.getElementById('post-title');
const postContentElement = document.getElementById('post-content');
const imageContainer = document.getElementById('image-container');
const deleteButton = document.getElementById('delete-button');
const editButton = document.getElementById('edit-button');
const likeButton = document.getElementById('like-button');
const reviewPostId = window.location.pathname.split('/').pop();

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

function renderImages(imageUrls) {
    for (const imageUrl of imageUrls) {
        const imageElement = document.createElement('img');
        imageElement.src = imageUrl;
        imageElement.classList.add('post-image');
        imageContainer.appendChild(imageElement);
    }
}

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
        deleteButton.textContent = '삭제';
        deleteButton.classList.add('delete-comment-button');
        deleteButton.setAttribute('data-comment-id', comment.id);
        commentElement.appendChild(deleteButton);

        const replyButton = document.createElement('button'); // 대댓글 버튼 생성
        replyButton.textContent = '대댓글';
        replyButton.classList.add('reply-comment-button');
        replyButton.setAttribute('data-comment-id', comment.id);
        commentElement.appendChild(replyButton); // 대댓글 버튼을 댓글 요소에 추가


        // 대댓글 목록을 표시
        if (comment.replyList && comment.replyList.length > 0) {
            const replyListElement = document.createElement('ul');
            replyListElement.classList.add('reply-list');

            for (const reply of comment.replyList) {
                const replyElement = document.createElement('li');
                replyElement.classList.add('comment');

                const replyContentElement = document.createElement('div');
                replyContentElement.classList.add('comment-content');
                replyContentElement.textContent = `${reply.nickName}: ${reply.content}`;
                replyElement.appendChild(replyContentElement);

                replyListElement.appendChild(replyElement);

                // Reply edit button
                const editReplyButton = document.createElement('button');
                editReplyButton.textContent = '수정';
                editReplyButton.classList.add('edit-reply-button');
                editReplyButton.setAttribute('data-reply-id', reply.id);
                editReplyButton.setAttribute('data-reply-content', reply.content);
                replyElement.appendChild(editReplyButton);

                // Reply delete button
                const deleteReplyButton = document.createElement('button');
                deleteReplyButton.textContent = '삭제';
                deleteReplyButton.classList.add('delete-reply-button');
                deleteReplyButton.setAttribute('data-reply-id', reply.id);
                replyElement.appendChild(deleteReplyButton);
            }

            commentElement.appendChild(replyListElement);
        }

        commentList.appendChild(commentElement);
    }
}

async function init() {
    const postData = await fetchPostData();

    if (postData) {
        postTitleElement.textContent = postData.title;
        postContentElement.textContent = postData.content;

        renderImages(postData.imageUrls);

        const likeCountElement = document.getElementById('like-count');
        likeCountElement.textContent = postData.likeCounts;

        updateLikeButton(postData.isLiked);

        deleteButton.addEventListener('click', deleteReviewPost);

        editButton.addEventListener('click', () => {
            window.location.href = `/view/review-post/update/${reviewPostId}`;
        });

        // 댓글 정보 표시
        renderComments(postData.commentList);

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
                location.reload();
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
const apiUrl = `/api/comments/review-posts/${reviewPostId}`;
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

const commentsApiUrl = `/api/comments/review-posts/${reviewPostId}`;


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

    if (e.target.classList.contains('reply-comment-button')) {
        const commentId = e.target.getAttribute('data-comment-id');
        openReplyForm(commentId); // 대댓글 입력 창 열기
    }

    // 대댓글 수정 및 삭제 버튼 이벤트 처리
    if (e.target.classList.contains('edit-reply-button')) {
        const replyId = e.target.getAttribute('data-reply-id');
        const replyContent = e.target.getAttribute('data-reply-content');
        openReplyEditForm(replyId); // 수정 폼 열기
    }

    if (e.target.classList.contains('delete-reply-button')) {
        const replyId = e.target.getAttribute('data-reply-id');
        deleteReply(replyId);
    }
});

// 수정 폼 열기 함수
function openEditForm(commentId) {
    const commentEditForm = document.getElementById('comment-edit-form');
    commentEditForm.style.display = 'block';

    const editCommentContent = document.getElementById('edit-comment-content');
    editCommentContent.value = ''; // 수정 내용 초기화

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
                // 댓글 수정에 실패한 경우
                alert(responseData.message);
            }
            location.reload(); // 페이지 리로드
        } else {
            console.error('Error updating comment:', response.statusText);
        }
    } catch (error) {
        console.error('Error updating comment:', error);
    }
}

function cancelEditComment() {
    closeEditForm();
}

function closeEditForm() {
    const commentEditForm = document.getElementById('comment-edit-form');
    commentEditForm.style.display = 'none';
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

// 대댓글 폼 열기
function openReplyForm(commentId) {
    const replyFormContainer = document.getElementById('reply-form-container');
    const replyForm = document.getElementById('reply-form');
    const replyContent = document.getElementById('reply-content');

    replyForm.style.display = 'block';
    replyContent.value = '';
    replyForm.setAttribute('data-comment-id', commentId);
}

// 대댓글 폼 닫기
function closeReplyForm() {
    const replyFormContainer = document.getElementById('reply-form-container');
    const replyForm = document.getElementById('reply-form');
    const replyContent = document.getElementById('reply-content');

    // 대댓글 입력창을 숨기도록 변경
    replyFormContainer.style.display = 'none';
    replyForm.removeAttribute('data-comment-id');

    // 대댓글 내용을 비우기
    replyContent.value = '';
}

// 취소 버튼 클릭 이벤트 처리
const cancelReplyButton = document.getElementById('cancel-reply-button');
cancelReplyButton.addEventListener('click', closeReplyForm);

// 대댓글 폼 제출 이벤트
const replyForm = document.getElementById('reply-form');
replyForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const content = document.getElementById('reply-content').value;
    const commentId = replyForm.getAttribute('data-comment-id');

    if (!content) {
        alert('대댓글 내용을 입력하세요.');
        return;
    }

    const requestData = {
        content: content
    };

    try {
        const response = await fetch(`/api/replies/${commentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const responseData = await response.json();
            if (responseData) {
                alert('대댓글이 성공적으로 생성되었습니다.');
                closeReplyForm();
            } else {
                console.error('대댓글 생성 실패');
            }

        } else {
            console.error('대댓글 생성 요청 실패:', response.statusText);
        }

    } catch (error) {
        console.error('대댓글 생성 요청 에러:', error);
    }
    location.reload();

});

function openReplyEditForm(replyId, replyContent) {
    const replyEditForm = document.getElementById('reply-edit-form');
    replyEditForm.style.display = 'block';

    const editReplyContent = document.getElementById('edit-reply-content');
    editReplyContent.value = "";

    const saveEditButton = document.getElementById('save-edit-reply-button');
    saveEditButton.addEventListener('click', () => {
        saveEditedReply(replyId);
    });

    const cancelEditButton = document.getElementById('cancel-edit-reply-button');
    cancelEditButton.addEventListener('click', cancelEditReply);
}


async function saveEditedReply(replyId) {
    const editReplyContent = document.getElementById('edit-reply-content').value;

    if (!editReplyContent) {
        alert('대댓글 내용을 입력하세요.');
        return;
    }

    const requestData = {
        content: editReplyContent
    };

    try {
        const response = await fetch(`/api/replies/${replyId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            const responseData = await response.json();
            if (responseData.status === 200) {
                alert('대댓글이 수정되었습니다.');
            } else {
                alert(responseData.message);
            }
            location.reload();
        } else {
            console.error('Error updating reply:', response.statusText);
        }

        fetchReplies(); // 대댓글 목록 다시 불러오기

    } catch (error) {
        console.error('Error updating reply:', error);
    } finally {
        closeReplyEditForm(); // 수정 폼 닫기
    }
}

// 대댓글 삭제 함수
async function deleteReply(replyId) {
    const confirmation = confirm('대댓글을 삭제하시겠습니까?');

    if (confirmation) {
        try {
            const response = await fetch(`/api/replies/${replyId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            if (response.ok) {
                const data = await response.json();
                if (data.status === 200) {
                    alert('대댓글이 삭제되었습니다.');
                } else {
                    alert(data.message);
                }
                location.reload();
            } else {
                console.error('Error deleting reply:', response.statusText);
            }

            fetchReplies(); // 대댓글 목록 다시 불러오기

        } catch (error) {
            console.error('Error deleting reply:', error);
        } finally {
            closeReplyEditForm(); // 수정 폼 닫기
        }
    }
}


function closeReplyEditForm() {
    const replyEditForm = document.getElementById('reply-edit-form');
    replyEditForm.style.display = 'none';
}


init();
