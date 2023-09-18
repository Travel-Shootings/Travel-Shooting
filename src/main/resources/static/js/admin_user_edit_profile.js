// user의 기존 nickname 가져오기
var userIdElement = document.getElementById('user-id');
var userId = userIdElement.getAttribute('data');

// user의 정보 가져오기
fetch('/api/admin/users/' + userId,  {
    method: 'GET',
    headers: {'Content-Type': 'application/json'}
}) // GET 요청으로 JSON 데이터 가져오기
    .then(response => response.json())
    .then(jsonData => {
        var nickname = document.getElementById("inputNickname");
        nickname.value = jsonData.nickname;
    })
    .catch(error => console.error('Error:', error));

// 유저 정보 수정
let idx = {
    init: function () {
        $("#edit-button").on("click", () => {
            this.editUserProfile();
        });
    },

    editUserProfile: function () {
        let data = {
            nickname: $('#inputNickname').val(),
            role: $('#select-box').val()
        };

        $.ajax({
            type: "PUT",
            url: "/api/admin/user/" + userId,
            data: JSON.stringify(data),
            contentType: "application/json; charset=urf-8",
            dataType: "json"
        })
            .done(function (res) {
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.close(); // 팝업 창 닫기
                    window.opener.location.reload(); // 부모 창 새로고침
                }
            })
            .fail(function () {
                alert("유저 정보 수정 실패. 모든 항목을 입력했는지 확인해주세요.")
            });
    }
}
idx.init();