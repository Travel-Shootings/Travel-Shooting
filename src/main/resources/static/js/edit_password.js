// 비밀번호 변경
let idx = {
    init: function () {
        $("#edit-button").on("click", () => {
            this.editPassword();
        });
    },

    editPassword: function () {
        let data = {
            oldPassword: $('#inputOldPassword').val(),
            newPassword: $('#inputNewPassword').val()
        };

        $.ajax({
            type: "PUT",
            url: "/api/my-page/edit/password",
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
                }
            })
            .fail(function (request, status, error, response) {
                console.log(status)
                console.log(error)
                alert(response.responseJSON.message);
            });
    }
}
idx.init();