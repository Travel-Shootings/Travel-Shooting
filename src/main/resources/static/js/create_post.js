$('#search-icon').on('click', function (e) {
    e.preventDefault();
    const location = $('#search-location').val();
    searchNaver(location);
});

function searchNaver(location) {
    $.ajax({
        url: `/api/server/naver?query=${location}`,
        type: 'GET',
        contentType: 'application/json',
        success: function (response) {
            if (response.length > 0) {
                // 장소 검색 결과가 있는 경우
                const resultsContainer = $('#search-results');
                resultsContainer.empty(); // 결과를 표시하기 전에 컨테이너를 비웁니다.

                $.each(response, function (index, result) {
                    // 각 장소 정보를 모달에 추가합니다.
                    const resultDiv = $('<div class="user-list-item">');
                    resultDiv.append('<span class="place-title">' + result.title + '</span>');
                    resultDiv.append('<span class="place-address">' + result.address + '</span>');
                    resultDiv.append('<span class="place-roadAddress">' + result.roadAddress + '</span>');
                    resultDiv.append('<button class="select-place-button" type="button"> 선택</button>');

                    resultsContainer.append(resultDiv);
                });

                // 모달을 열고 내용을 설정
                const modal = document.querySelector('.search-modal');
                modal.style.display = "flex";
            } else {
                // 선택한 장소가 없는 경우
                alert('검색할 장소를 입력하세요.');
            }
        },
        error: function () {
            alert('검색어를 입력하세요.');
        }
    });
}


//지도를 그려주는 함수 실행
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
$('#submit').on('click', function (e) {
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


// 모달 열기 버튼 클릭시 이벤트 발생
const btnOpenModal = document.querySelector('.create-journey');
btnOpenModal.addEventListener("click", () => {
    // 모달 열기
    const modal = document.querySelector('.first-modal');
    modal.style.display = "flex";
});

// 모달 닫기 버튼 클릭 시
const btnCloseModal = document.querySelector('#cancel-journey');
btnCloseModal.addEventListener('click', function (e) {
    e.preventDefault();

    // 모달 닫기
    const modal = document.querySelector('.first-modal');
    modal.style.display = 'none';
});

// 첫번째 모달 입력값 -> 게시글 작성의 JourneyList로 옮기는 매서드
$(document).ready(function () {
    $("#post-journey").click(function (e) {
        e.preventDefault(); // 폼의 기본 동작(페이지 새로고침)을 막음

        // 여행 정보 입력 폼에서 값을 가져옴
        const location = $("#location").val();
        const budget = $("#budget").val();
        const startJourney = $("#journeyStartDate").val();
        const endJourney = $("#journeyEndDate").val();
        const members = $("#member").val();
        const modaladdress = $("#modaladdress").val();

        const formattedStartJourney = formatDateToCustomFormat(startJourney);
        const formattedEndJourney = formatDateToCustomFormat(endJourney);

        // 날짜 차이 계산
        const startJourneyHandler = new Date($("#journeyStartDate").val());
        const endJourneyHandler = new Date($("#journeyEndDate").val());
        const timeDiff = Math.abs(endJourneyHandler - startJourneyHandler);
        const daysHandler = Math.floor(timeDiff / (1000 * 60 * 60 * 24)); // 일자 계산

        const budgetHandler = parseInt($("#budget").val()) || 0;
        const membersHandler = parseInt($("#member").val()) || 0;

        // 기존 총 일정 정보를 가져옴
        let totalBudget = parseInt($("#totalBudget").text().split('원')[0]) || 0;
        let totalDays = parseInt($("#totalDays").text().split('일')[0]) || 0;
        let totalMembers = parseInt($("#totalMembers").text().split('명')[0]) || 0;

        // 계산 결과를 업데이트
        totalBudget += budgetHandler;
        totalDays += daysHandler;
        totalMembers += membersHandler;

        // 업데이트된 결과를 HTML에 반영
        $("#totalBudget").text(totalBudget + "원");
        $("#totalDays").text(totalDays + "일");
        $("#totalMembers").text(totalMembers + "명");

        console.log(totalBudget)
        console.log(totalDays)
        console.log(totalMembers)

        // 새로운 일정 정보를 추가할 HTML 생성
        const newRow = `<tr>
            <td class="id" style="display:none;"></td>
            <td class="place">${location}</td>
            <td class="budget">${budget}</td>
            <td class="formattedStartJourney">${formattedStartJourney}</td>
            <td class="formattedEndJourney">${formattedEndJourney}</td>
            <td class="members">${members}</td>
            <td class="startJourney" style="display: none;">${startJourney}</td>
            <td class="endJourney" style="display: none;">${endJourney}</td>
            <td class="placeAddress" style="display: none;">${modaladdress}</td>
            <td class="edit">
                <button class="edit-item-btn" type="button" style="font-size: 15px">Edit</button>
            </td>
            <td class="remove">
                <button class="remove-item-btn" type="button" style="font-size: 15px">Remove</button>
            </td>
        </tr>`;

        // HTML을 목록에 추가
        $(".list").append(newRow);

        // 입력 필드 초기화
        $("#location").val('');
        $("#budget").val('');
        $("#journeyStartDate").val('');
        $("#journeyEndDate").val('');
        $("#member").val('');
        $("#modaladdress").val('');

        // "id='post-journey'" 버튼을 클릭하면 주소값 설정 및 검색 버튼(id='submit')을 클릭
        $("#address").val(modaladdress);
        $("#submit").click();

        const modal = document.querySelector('.first-modal');
        modal.style.display = 'none';
    });
});

// 여행 리스트 추가 후 삭제 기능(프론트)
$(document).ready(function () {
    // 삭제 버튼에 대한 클릭 이벤트 핸들러 설정
    $(".list").on("click", ".remove-item-btn", function () {
        // 클릭한 삭제 버튼이 속한 행을 찾아서 삭제
        $(this).closest("tr").remove();
    });
});

// 여행 시작일 & 여행 종료일 포멧 형식 변환 (YYYY년-MM월-DD일-HH시-MM분)
function formatDateToCustomFormat(dateString) {
    const dateObj = new Date(dateString);

    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 두 자리로 포맷팅
    const day = String(dateObj.getDate()).padStart(2, '0'); // 일도 두 자리로 포맷팅
    const hours = String(dateObj.getHours()).padStart(2, '0');
    const minutes = String(dateObj.getMinutes()).padStart(2, '0');

    const formattedDate = `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;

    return formattedDate;
}


// 2번째 모달(네이버 검색 결과 창) 닫기 버튼 클릭 시
const btnCloseSecondModal = document.querySelector('.close-second-modal');  /*  '.search-modal' 설정할 경우 클래스 어디를 눌러도 모달 닫힘 발동 */
btnCloseSecondModal.addEventListener('click', function () {
    const modal = document.querySelector('.search-modal');
    modal.style.display = 'none';
});

// 네이버 검새 결과 모달창에서 [선택] 버튼을 누르면 발생하는 매서드
$(document).on('click', '.select-place-button', function () {
    // 선택한 장소 정보 가져오기
    const selectedPlace = $(this).closest('.user-list-item').find('.place-title').text();
    const selectedAddress = $(this).closest('.user-list-item').find('.place-address').text();

    // id가 "location"인 입력란에 선택한 장소 정보 설정 & id가 "modaladdress"인 입력란에 선택한 주소 정보 설정
    $('#location').val(selectedPlace);
    $('#modaladdress').val(selectedAddress);

    // 모달 닫기 (선택한 정보를 입력란에 설정한 후 모달을 닫을 수 있음)
    const modal = document.querySelector('.search-modal');
    modal.style.display = 'none';
});





// 최종 게시글 작성 버튼 눌렀을 때 백엔드의 CreatePostAndJourneyList로 보내는 로직
let idx = {
    init: function () {
        $("#addPost").on("click", (event) => {
            event.preventDefault();
            this.newPost();
        });
    },

    newPost: function () {

        const title = $('#title').val();
        const contents = $('#contents').val();

        // 프론트엔드의 테이블에서 값을 가져옴
        const journeyListRequestDtos = [];

        $(".list tr").each(function () {
            const locations = $(this).find(".place").text();
            const budget = $(this).find(".budget").text();
            const startJourney = $(this).find(".startJourney").text();
            const endJourney = $(this).find(".endJourney").text();
            const members = $(this).find(".members").text();
            const placeAddress = $(this).find(".placeAddress").text();

            const dto = {
                locations: locations,
                budget: budget,
                startJourney: startJourney,
                endJourney: endJourney,
                members: members,
                placeAddress: placeAddress
            };

            journeyListRequestDtos.push(dto);
        });

        let data = {
            postRequestDto: {
                title: title,
                contents: contents,
            },
            journeyListRequestDtos: journeyListRequestDtos
        };
        console.log(data);

        $.ajax({
            type: "POST",
            url: "/api/posts",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        })
            .done(function () {
                alert("글 작성 성공");
                window.location.href = "/view/post";
            })
            .fail(function (response) {
                console.log(response);
                alert("글 작성 실패");
            })
    }
}
idx.init();

// 취소 버튼 누를시 메인 페이지로 이동
$(document).ready(function () {
    $("#cancelPost").click(function (e) {
        e.preventDefault(); // 폼의 기본 동작(페이지 새로고침)을 막음
        window.location.href = "http://localhost:8080/view/home"; // 메인 페이지로 이동
    });
});


// 현재 문제가 있어서 주석 처리 중
// $(document).ready(function () {
//     // 편집 버튼에 대한 클릭 이벤트 핸들러 설정
//     $(".list").on("click", ".edit-item-btn", function (e) {
//         e.preventDefault(); // 폼의 기본 동작(페이지 새로고침)을 막음
//
//         // 클릭한 편집 버튼이 속한 행을 찾습니다.
//         const row = $(this).closest("tr");
//
//         // 해당 행의 값을 가져와서 모달에 채웁니다.
//         const location = row.find(".place").text();
//         const budget = row.find(".budget").text();
//         const startJourney = row.find(".startJourney").text();
//         const endJourney = row.find(".endJourney").text();
//         const members = row.find(".members").text();
//         const placeAddress = row.find(".placeAddress").text();
//
//         // 모달에 값을 채웁니다.
//         $("#location").val(location);
//         $("#budget").val(budget);
//         $("#journeyStartDate").val(startJourney);
//         $("#journeyEndDate").val(endJourney);
//         $("#member").val(members);
//         $("#modaladdress").val(placeAddress);
//
//         // 모달을 엽니다.
//         const modal = document.querySelector('.modal');
//         modal.style.display = 'flex';
//
//         // 저장 버튼에 클릭 이벤트 핸들러를 추가하여 변경된 내용을 테이블에 반영합니다.
//         $("#post-journey").off("click"); // 이전에 등록된 클릭 핸들러 제거
//         $("#post-journey").on("click", function (e) {
//             e.preventDefault();
//
//             // 모달에서 변경된 값을 가져옵니다.
//             const editedLocation = $("#location").val();
//             const editedBudget = $("#budget").val();
//             const editedStartJourney = $("#journeyStartDate").val();
//             const editedEndJourney = $("#journeyEndDate").val();
//             const editedMembers = $("#member").val();
//             const editedPlaceAddress = $("#modaladdress").val();
//
//             // 테이블에 변경된 값을 반영합니다.
//             row.find(".place").text(editedLocation);
//             row.find(".budget").text(editedBudget);
//             row.find(".startJourney").text(editedStartJourney);
//             row.find(".endJourney").text(editedEndJourney);
//             row.find(".members").text(editedMembers);
//             row.find(".placeAddress").text(editedPlaceAddress);
//
//             // 모달을 닫습니다.
//             modal.style.display = 'none';
//         });
//     });
// });