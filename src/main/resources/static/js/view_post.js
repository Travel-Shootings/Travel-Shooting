// 글 데이터 가져오기


const postId = window.location.pathname.split('/').pop();

$(document).ready(function () {
    loadPostData()
});

function loadPostData() {
    fetch('/api/posts/' + postId, {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            let nickName = data.nickName;
            let livingPlace = data.region;
            let createdAt = data.createdAt;
            let title = data.title
            let contents = data.contents
            let commentList = data.commentList;
            let journeyList = data.journeyList; // journeyList 데이터 가져오기

            // 가져온 데이터를 HTML에 추가
            $('.topper mgn').text("닉네임: " + nickName);
            $('.topper mgn2').text("지역: " + livingPlace);
            $('.title temp').text(title); // .title 요소 안의 temp 클래스를 가진 요소에 title 값을 설정합니다.
            $('.post temp').text(contents); // .post 요소 안의 temp 클래스를 가진 요소에 contents 값을 설정합니다.

            if (commentList && commentList.length > 0) {
                let commentListHtml = '';
                journeyList.forEach(item => {
                    const nickName = item.nickName;
                    const content = item.content;

                    commentListHtml += $('.comment temp').text(nickName + ": " + content);
                })
            }

            // 프론트엔드에서 journeyList 데이터를 반복하며 표시
            if (journeyList && journeyList.length > 0) {
                let journeyListHtml = '';
                journeyList.forEach((item, index) => {
                    // 각 항목에서 필요한 데이터 가져오기
                    const location = item.locations;
                    const budget = item.budget;
                    const members = item.members;
                    const startJourney = new Date(item.startJourney);
                    const endJourney = new Date(item.endJourney);
                    const placeAddress = item.placeAddress;

                    // 날짜를 YYYY년 MM월 DD일 HH시 MM분으로 표기하는 함수
                    const startFormatted = startJourney.toLocaleString('ko-KR', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: 'numeric',
                        minute: 'numeric',
                    });
                    const endFormatted = endJourney.toLocaleString('ko-KR', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: 'numeric',
                        minute: 'numeric',
                    });

                    // LocalDateTime 형식의 createdAt을 "nov 18, 2023" 형태로 변환
                    const months = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
                    const createdAtDate = new Date(createdAt); // LocalDateTime을 JavaScript Date 객체로 변환
                    const monthName = months[createdAtDate.getMonth()]; // 월 이름 가져오기
                    const formattedDate = `${monthName} ${createdAtDate.getDate()}, ${createdAtDate.getFullYear()}`;
                    $('.perm').text("작성일: "+ formattedDate);


                    // HTML에 동적으로 추가
                    journeyListHtml += `<box> ${index + 1 + "번째 여행지"}
            <ciro>
                <circ></circ>
            </ciro>
            <nm>장소명: ${location}</nm>
            <nm>예산: ${budget}원</nm>
            <nm>인원: ${members}</nm>
            <nm>일정: ${startFormatted} ~ ${endFormatted}</nm>
            <nm>주소: ${placeAddress}</nm>
        </box>`;
                });
                $('.journeyList').html(journeyListHtml); // .journeyList 요소 안에 journeyList 데이터 추가
            }
        })
}


// 네이버 지도 API 함수
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
$('#search-address').on('click', function (e) {
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

