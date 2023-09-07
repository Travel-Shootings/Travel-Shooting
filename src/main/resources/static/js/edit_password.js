// // 비밀번호 변경
// let idx = {
//     init: function () {
//         $("#edit-button").on("click", () => {
//             const confirmResult = confirm("수정하시겠습니까? 확인을 누르시면 재로그인 해야 합니다.");
//             if(confirmResult) {
//                 this.editPassword();
//             }
//         });
//     },
//
//     editPassword: function () {
//         let data = {
//             oldPassword: $('#inputOldPassword').val(),
//             newPassword: $('#inputNewPassword').val()
//         };
//
//         $.ajax({
//             type: "PUT",
//             url: "/api/my-page/edit/password",
//             data: JSON.stringify(data),
//             contentType: "application/json; charset=urf-8",
//             dataType: "json"
//         })
//             .done(function (res) {
//                 console.log(res)
//                 console.log(res.statusCode)
//                 if (res.statusCode === 200) {
//                     alert(res.message)
//                     window.close(); // 팝업 창 닫기
//                     window.opener.logout(); // 부모 창 새로고침
//                 }
//             })
//             .fail(function (request, status, error, response) {
//                 console.log(status)
//                 console.log(error)
//                 alert(response.responseJSON.message);
//             });
//     }
// }
// idx.init();

function editPassword() {
    const confirmResult = confirm("수정하시겠습니까? 확인을 누르시면 재로그인 해야 합니다.");

    if (confirmResult) {
        let data = {
            oldPassword: $('#inputOldPassword').val(),
            newPassword: $('#inputNewPassword').val()
        };

        console.log(data.oldPassword);

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
                    window.opener.logout();
                }
            })
            .fail(function (jqXHR, textStatus, errorThrown) {
                console.log("Request failed:");
                console.log("Status: " + textStatus);
                console.log("Error thrown: " + errorThrown);
                if (jqXHR.responseJSON) {
                    console.log("Response: " + JSON.stringify(jqXHR.responseJSON));
                }
                alert(JSON.stringify(jqXHR.responseJSON.message))
            });
    }
}

