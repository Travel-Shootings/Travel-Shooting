function editPassword() {
    const confirmResult = confirm("수정하시겠습니까? 확인을 누르시면 재로그인 해야 합니다.");

    if (confirmResult) {
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
                if (res.statusCode === 200) {
                    alert(res.message)
                    window.close(); // 팝업 창 닫기
                    window.opener.logout();
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                alert(JSON.stringify(jqXHR.responseJSON.message))
            });
    }
}

