var userRoleElement = document.getElementById('user-role');
var userRole = userRoleElement.getAttribute('data');

window.onload = function() {
    var login_btn = document.getElementById("login-btn");
    var signup_btn = document.getElementById("signup-btn");
    var mypage_btn = document.getElementById("mypage-btn");
    var notification_btn = document.getElementById("notification-btn");

    var admin_box = document.getElementById("admin-box");


    if (checkAuthorizationCookie()) {
        login_btn.style.display = "none";
        signup_btn.style.display = "none";

        mypage_btn.style.display = "block";
        notification_btn.style.display = "block";
    }
    if (userRole === "ADMIN") {
        admin_box.style.display = "block";
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
}

// 홈페이지 버튼 상호작용 관련
// Side Menu
const sideNav = document.querySelector('.sidenav');
M.Sidenav.init(sideNav, {});

// Slider
const slider = document.querySelector('.slider');
M.Slider.init(slider, {
    indicators: false,
    height: 500,
    transition: 500,
    interval: 6000
});

// Scrollspy
const ss = document.querySelectorAll('.scrollspy');
M.ScrollSpy.init(ss, {});

// Material Boxed
const mb = document.querySelectorAll('.materialboxed');
M.Materialbox.init(mb, {});

// Auto Complete
const ac = document.querySelector('.autocomplete');
M.Autocomplete.init(ac, {
    data: {
        "Aruba": null,
        "Cancun Mexico": null,
        "Hawaii": null,
        "Florida": null,
        "California": null,
        "Jamaica": null,
        "Europe": null,
        "The Bahamas": null,
    }
});

// logout
let idx = {
    init: function () {
        $("#user-menu-logout").on("click", () => {
            this.logout();
        });
    },

    logout: function () {
        $.ajax({
            type: "DELETE",
            url: "/api/user/logout",
            contentType: "application/json; charset=urf-8",
            dataType: "json"
        })
            .done(function (res) {
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.location.href = "/view/home";
                }
            })
            .fail(function (request, status, error) {
                alert("로그아웃에 실패했습니다.");
            });
    }
}
idx.init();

$(document).ready(function () {
    loadThreePosts()
});

// JavaScript
$(document).ready(function () {
    loadThreePosts();
});

// loadThreePosts 함수 내에서 title과 contents를 원하는 길이로 자르고 표시하는 코드
function loadThreePosts() {
    fetch('/api/posts/three', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => {
            if (!response.ok) {
                alert("게시글을 불러오는데 오류가 발생했습니다.");
                window.location.href = "/view/post";
            }
            return response.json();
        })
        .then(data => {
            const cardPanels = document.querySelectorAll('.card-panel');

            // 가져온 데이터를 반복하며 각 요소에 값을 설정합니다.
            data.forEach((post, index) => {
                const cardPanel = cardPanels[index];
                const titleElement = document.querySelectorAll('.card-panel h4')[index];
                // const contentElement = document.querySelectorAll('.card-panel p')[index];

                // title과 contents를 원하는 길이로 자릅니다.
                const maxTitleLength = 30; // 원하는 title 최대 길이
                // const maxContentLength = 10; // 원하는 contents 최대 길이

                const truncatedTitle = post.title.substring(0, maxTitleLength);
                // const truncatedContent = post.contents.substring(0, maxContentLength);

                // titleElement와 contentElement에 데이터를 설정합니다.
                titleElement.textContent = truncatedTitle;
                // contentElement.textContent = truncatedContent;

                // card-panel을 클릭할 때 상세 페이지로 이동하도록 이벤트 리스너를 추가합니다.
                cardPanel.addEventListener('click', function () {
                    window.location.href = `/view/post/${post.id}`;
                });
            });
        })
        .catch(error => {
            alert(error.message);
            console.error('데이터를 불러오는 중 오류 발생:', error);
        });
}

$(document).ready(function () {
    loadSixReviewPosts();
});

function loadSixReviewPosts() {
    fetch('/api/review-posts/six', {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => {
            if (!response.ok) {
                alert("리뷰를 불러오는데 오류가 발생했습니다.");
                window.location.href = "/view/review-post";
            }
            return response.json();
        })
        .then(data => {
            const cardPanels = document.querySelectorAll('.review-card .card-panel2');

            // 가져온 데이터를 반복하며 각 요소에 값을 설정합니다.
            data.forEach((review, index) => {
                const cardPanel = cardPanels[index];
                const contentElement = cardPanel.querySelector('p');

                // contentElement에 이미지 URL이 있는지 확인하고, 없을 경우 기본 이미지를 설정합니다.
                if (review.imageUrls && review.imageUrls.length > 0) {
                    contentElement.innerHTML = `<img src="${review.imageUrls[0]}" alt="Review Image" style="max-width: 100%;">`;
                } else {
                    contentElement.innerHTML = `<img src="https://travelshooting.s3.ap-northeast-2.amazonaws.com/Travel+-+Shooting.jpg" alt="Review Image" style="max-width: 100%;">`;

                }

                // card-panel을 클릭할 때 상세 페이지로 이동하도록 이벤트 리스너를 추가합니다.
                cardPanel.addEventListener('click', function () {
                    window.location.href = `/view/review-post/${review.id}`;
                });
            });
        })
        .catch(error => {
            alert(error.message)
            console.error('데이터를 불러오는 중 오류 발생:', error);
        });
}





document.getElementById('notification-btn').addEventListener('click', function () {
    var popup = document.getElementById('notification-popup');
    if (popup.style.display === 'block') {
        popup.style.display = 'none';
    } else {
        popup.style.display = 'block';
    }
});

// 다른 곳을 클릭하면 팝업이 닫히도록
document.addEventListener('click', function (event) {
    var popup = document.getElementById('notification-popup');
    var btn = document.getElementById('notification-btn');
    if (event.target !== btn && !btn.contains(event.target) && event.target !== popup && !popup.contains(event.target)) {
        popup.style.display = 'none';
    }
});


var userIdElement = document.getElementById('user-id');
var userId = userIdElement.getAttribute('data');
$(document).ready(function() {
    loadNotificationData()
});
function loadNotificationData() {
    fetch('/api/notifications/'+ userId + '/uncheck', {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            $('#notification-box').empty()

            let length = data.length - 4;

            for (let i = data.length - 1; i > length; i--) {
                let notificationId = data[i].id;
                let message = data[i].message;
                let postId;
                if (data[i].postId != null) {
                    postId = data[i].postId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/post/${postId}">${message}</a></div>`

                    $('#notification-box').append(temp_html);
                } else {
                    postId = data[i].reviewPostId;
                    let temp_html = `<div id="notification" onclick="confirmNotification(${notificationId})"><a href="/view/review-post/${postId}">${message}</a></div>`

                    $('#notification-box').append(temp_html);
                }


            }

        })
}

function confirmNotification(notificationId) {
    fetch('/api/notifications/' + notificationId + '/read', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
    })
        .then(response => response.json())
}
