// Enter 키 이벤트 핸들러 추가
$('#search-location').on('keydown', function (e) {
    if (e.key === 'Enter') {
        e.preventDefault();
        $('#search-icon').click(); // 검색 아이콘을 클릭하도록 함
    }
});

// 검색 아이콘 클릭 시 이벤트 핸들러
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
    // 총 일정 정보 초기화 함수
    function resetTotalTravelInfo() {
        $("#totalBudget").text("0원");
        $("#totalDays").text("0일 0시간 0분");
        $("#totalDays").data('days', 0);
        $("#totalDays").data('hours', 0);
        $("#totalDays").data('minutes', 0);
    }

    // 여행 정보 추가 함수
    function addTravelInfo(location, budget, startJourney, endJourney, members, modaladdress) {
        const startJourneyObj = formatDateToCustomFormat(startJourney);
        const endJourneyObj = formatDateToCustomFormat(endJourney);

        // 날짜 차이 계산
        const startJourneyHandler = new Date(startJourney);
        const endJourneyHandler = new Date(endJourney);
        const timeDiff = Math.abs(endJourneyHandler - startJourneyHandler);
        const daysHandler = Math.floor(timeDiff / (1000 * 60 * 60 * 24)); // 일자 계산
        const hoursHandler = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)); // 시간 계산
        const minutesHandler = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60)); // 분 계산

        // 기존 총 일정 정보를 가져옴
        let totalBudget = parseInt($("#totalBudget").text().replace('원', '')) || 0;
        let totalDays = parseInt($("#totalDays").data('days')) || 0;
        let totalHours = parseInt($("#totalDays").data('hours')) || 0;
        let totalMinutes = parseInt($("#totalDays").data('minutes')) || 0;

        // 계산 결과를 업데이트
        totalBudget += parseInt(budget) || 0;
        totalDays += daysHandler;
        totalHours += hoursHandler;
        totalMinutes += minutesHandler;

        // 업데이트된 결과를 HTML에 반영
        $("#totalBudget").text(totalBudget + "원");
        $("#totalDays").text(`${totalDays}일 ${totalHours}시간 ${totalMinutes}분`);

        // 데이터 속성에 총 일자, 시간, 분 단위 저장
        $("#totalDays").data('days', totalDays);
        $("#totalDays").data('hours', totalHours);
        $("#totalDays").data('minutes', totalMinutes);

        // 새로운 일정 정보를 추가할 HTML 생성
        const newRow = `<tr>
        <td class="id" style="display:none;"></td>
        <td class="place" style="text-align: center;">${location}</td>
        <td class="budget" style="text-align: center;">${budget}</td>
        <td class="formattedStartJourney">${startJourneyObj.year}년 ${startJourneyObj.month}월 ${startJourneyObj.day}일 ${startJourneyObj.hours}시 ${startJourneyObj.minutes}분</td>
        <td class="formattedEndJourney">${endJourneyObj.year}년 ${endJourneyObj.month}월 ${endJourneyObj.day}일 ${endJourneyObj.hours}시 ${endJourneyObj.minutes}분</td>
        <td class="members" style="text-align: center;">${members}</td>
        <td class="startJourney" style="display: none;">${startJourney}</td>
        <td class="endJourney" style="display: none;">${endJourney}</td>
        <td class="placeAddress" style="display: none;">${modaladdress}</td>
        <td class="remove">
            <button class="remove-item-btn" type="button" style="font-size: 15px">삭제</button>
        </td>
    </tr>`;

        // HTML을 목록에 추가
        $(".list").append(newRow);

        // 주소 값을 네이버 지도 검색에 주입
        searchAddressToCoordinate(modaladdress);
    }

    // 여행 정보 삭제 함수
    function removeTravelInfo(row) {
        // 삭제할 항목의 정보를 가져옴
        const startDateHandler = new Date(row.find(".startJourney").text());
        const endDateHandler = new Date(row.find(".endJourney").text());
        const timeDiff = Math.abs(endDateHandler - startDateHandler);

        // 시간 차이를 일자로 계산
        const daysHandler = Math.floor(timeDiff / (1000 * 60 * 60 * 24));

        // 삭제한 항목의 정보를 업데이트
        const budgetHandler = parseInt(row.find(".budget").text()) || 0;

        // 기존 총 일정 정보를 가져옴
        let totalBudget = parseInt($("#totalBudget").text().replace('원', '')) || 0;
        let totalDays = parseInt($("#totalDays").data('days')) || 0;
        let totalHours = parseInt($("#totalDays").data('hours')) || 0;
        let totalMinutes = parseInt($("#totalDays").data('minutes')) || 0;

        // 계산 결과를 업데이트 (음수를 방지하기 위해 각 항목 별로 계산 후 빼기)
        totalBudget -= budgetHandler;
        totalDays -= daysHandler;
        totalHours -= Math.floor(timeDiff / (1000 * 60 * 60));
        totalMinutes -= Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));

        // 음수 값을 0으로 변경 (시간, 분 단위 계산에 의해 발생할 수 있음)
        totalDays = Math.max(totalDays, 0);
        totalHours = Math.max(totalHours, 0);
        totalMinutes = Math.max(totalMinutes, 0);

        // 업데이트된 결과를 HTML에 반영
        $("#totalBudget").text(totalBudget + "원");
        $("#totalDays").text(`${totalDays}일 ${totalHours}시간 ${totalMinutes}분`);

        // 데이터 속성에 총 일자, 시간, 분 단위 저장
        $("#totalDays").data('days', totalDays);
        $("#totalDays").data('hours', totalHours);
        $("#totalDays").data('minutes', totalMinutes);

        // 행을 삭제
        row.remove();
    }

    // 여행 정보 추가 버튼 클릭 시
    $("#post-journey").click(function (e) {
        e.preventDefault();
        const location = $("#location").val();
        const budget = $("#budget").val();
        const startJourney = $("#journeyStartDate").val();
        const endJourney = $("#journeyEndDate").val();
        const members = $("#member").val();
        const modaladdress = $("#modaladdress").val();

        addTravelInfo(location, budget, startJourney, endJourney, members, modaladdress);

        // 입력 필드 초기화
        $("#search-location").val('');
        $("#location").val('');
        $("#budget").val('');
        $("#journeyStartDate").val('');
        $("#journeyEndDate").val('');
        $("#member").val('');
        $("#modaladdress").val('');

        const modal = document.querySelector('.first-modal');
        modal.style.display = 'none';
        handleInputChange(); // 모달을 닫은 후에 입력값 변경 감시 함수 호출
    });

    // 여행 리스트 추가 후 삭제 기능(프론트)
    $(".list").on("click", ".remove-item-btn", function () {
        // 삭제 버튼을 누른 행을 찾음
        const row = $(this).closest("tr");
        removeTravelInfo(row);
    });

    // 입력 필드 값 변경 감시
    const modalCategoryWrapper = document.querySelector('.modal-category-wrapper');
    const inputs = modalCategoryWrapper.querySelectorAll('input');

    function handleInputChange() {
        // 입력 필드가 비어 있는지 확인
        let isEmpty = false;
        inputs.forEach(function (input) {
            if (input.value === '') {
                isEmpty = true;
            }
        });

        // 모든 필드가 채워져 있으면 버튼 활성화
        $("#post-journey").prop("disabled", isEmpty);
    }

    modalCategoryWrapper.addEventListener('input', handleInputChange);

    // 페이지 로드 시 총 일정 정보 초기화
    resetTotalTravelInfo();
});

// 여행 시작일 & 여행 종료일 포멧 형식 변환 (YYYY년-MM월-DD일-HH시-MM분)
function formatDateToCustomFormat(dateString) {
    const dateObj = new Date(dateString);

    const year = String(dateObj.getFullYear()).slice(-2); // 마지막 두 자리만 추출
    const month = String(dateObj.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더하고 두 자리로 포맷팅
    const day = String(dateObj.getDate()).padStart(2, '0'); // 일도 두 자리로 포맷팅
    const hours = String(dateObj.getHours()).padStart(2, '0');
    const minutes = String(dateObj.getMinutes()).padStart(2, '0');

    return { year, month, day, hours, minutes };
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
        // 입력값 가져오기
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

        // 검증 로직 추가
        if (!title) {
            alert("글 제목을 작성 하셔야 합니다.")
            return; // 오류 발생 시 중단
        }

        if (!contents) {
            alert("본문을 작성 하셔야 합니다.")
            return; // 오류 발생 시 중단
        }

        if (journeyListRequestDtos.length === 0) {
            alert("최소 1개 이상의 여행 일정을 추가 하셔야 합니다.")
            return; // 오류 발생 시 중단
        }

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

// 취소 버튼 누를시 게시글 페이지로 이동
$(document).ready(function () {
    $("#cancelPost").click(function (e) {
        e.preventDefault(); // 폼의 기본 동작(페이지 새로고침)을 막음
        window.location.href = "/view/post"; // 게시글 목록으로 이동
    });
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

// 전역 변수로 지도를 초기화
var map = new naver.maps.Map('map', {
    center: new naver.maps.LatLng(37.3595704, 127.105399),
    zoom: 10
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
    naver.maps.Service.geocode({
        query: address
    }, function (status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('잘못된 입력입니다.');
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

        // 정보 창을 열고 내용을 설정하여 지도에 표시
        infoWindow.setContent([
            '<div style="padding:10px;min-width:200px;line-height:150%;">',
            '<h5 style="margin-top:5px;">검색 주소 : ' + address + '</h5><br />',
            htmlAddresses.join('<br />'),
            '</div>'
        ].join('\n'));

        // 정보 창을 열 위치 설정
        infoWindow.open(map, point);

        // 지도 중심 이동
        map.setCenter(point);

        // 주소 정보를 표시하고 지도에 추가
        insertAddress(item.roadAddress, item.x, item.y);
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

    var point = new naver.maps.LatLng(longitude, latitude);
    new naver.maps.Marker({
        map: map,
        position: point
    });
}