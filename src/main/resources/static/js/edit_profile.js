// 프로필 수정
let idx = {
    init: function () {
        $("#edit-button").on("click", () => {
            this.editProfile();
        });
    },

    editProfile: function () {
        let data = {
            nickname: $('#inputNickname').val(),
            region: $('#select-box').val()
        };

        $.ajax({
            type: "PUT",
            url: "/api/my-page/edit",
            data: JSON.stringify(data),
            contentType: "application/json; charset=urf-8",
            dataType: "json"
        })
            .done(function (res) {
                console.log(res)
                console.log(res.statusCode)
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.close(); // 팝업 창 닫기
                    window.opener.location.reload(); // 부모 창 새로고침
                } else if (res.statusCode === 400) {
                    alert(res.message)
                }
            })
            .fail(function (request, status, error) {
                console.log(status)
                console.log(error)
                alert("프로필 변경에 실패했습니다.");
            });
    }
}
idx.init();