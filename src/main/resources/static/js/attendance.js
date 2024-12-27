// 달력에 데이터 띄우눈 애
document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');
    const modalEl = new bootstrap.Modal(document.getElementById('eventModal'), {}); // Bootstrap 모달 객체 생성

    const calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,dayGridWeek,dayGridDay'
        },
        initialDate: '2024-12-17',
        navLinks: true,
        editable: false,
        dayMaxEvents: true, // "더보기" 링크

        events: function (fetchInfo, successCallback, failureCallback) {
            fetch('/api/calendar')
                .then((response) => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! status: ${response.status}`);
                    }
                    return response.json();
                })
                .then((data) => {
                    const seenEvents = new Set(); // 중복 방지를 위한 Set
                    const events = [];
                    data.forEach(item => {
                        const uniqueKey = `${item.employeeDTO?.name}-${item.dayOffDTO?.leaveStartDate}-${item.dayOffDTO?.leaveType}`;
                        // 각 일정의 고유 키 생성
                        // console.log('uniqueKey : ' + uniqueKey);

                        if (!seenEvents.has(uniqueKey)) { // 중복 확인
                            events.push({
                                title: `${item.employeeDTO?.name || 'Unknown'} ${item.humanDTO?.position || ''}`,
                                start: item.dayOffDTO?.leaveStartDate,
                                backgroundColor: item.dayOffDTO?.leaveType === 2 ? 'green' :
                                    item.dayOffDTO?.leaveType === 1 ? 'lightgreen' :
                                        'orange',
                                extendedProps: {
                                    type: item.dayOffDTO?.leaveType === 2 ? '연차' :
                                        item.dayOffDTO?.leaveType === 1 ? '반차' :
                                            '연장근무',
                                    name: item.employeeDTO?.name || 'Unknown',
                                    department: item.departmentDTO?.depName || 'N/A',
                                    position: item.humanDTO?.position || 'N/A',
                                    // memo: item.dayOffDTO?.leaveMemo || '없음',
                                    startDate: item.dayOffDTO?.leaveStartDate,
                                    endDate: item.dayOffDTO?.leaveEndDate
                                }
                            });
                            console.log('events : ' + events);
                            seenEvents.add(uniqueKey); // 고유 키를 Set에 추가
                        }
                    });

                    successCallback(events); // 중복 제거 후 FullCalendar에 이벤트 전달
                })
                .catch((error) => {
                    console.error('Error fetching calendar events:', error);
                    failureCallback(error);
                });
        },

        // 클릭 했을 때 상세 정보 나오는 애
        eventClick: function (info) {
            const props = info.event.extendedProps;

            // 시간 변환 함수
            function formatLocalDateTime(isoString) {
                if (!isoString) return "N/A"; // 데이터가 없을 경우 표시
                const options = {
                    year: "numeric",
                    month: "2-digit",
                    day: "2-digit",
                    hour: "2-digit",
                    minute: "2-digit",
                    second: "2-digit",
                    hour12: false
                };
                return new Date(isoString).toLocaleString("ko-KR", options);
            }

            // 모달 내용 업데이트
            document.getElementById('eventType').textContent = props.type || '없음';
            document.getElementById('eventName').textContent = props.name || '없음';
            document.getElementById('eventDepartment').textContent = props.department || '없음';
            document.getElementById('eventPosition').textContent = props.position || '없음';

            // 날짜 출력 변환
            document.getElementById('eventStart').textContent = formatLocalDateTime(props.startDate);
            document.getElementById('eventEnd').textContent = formatLocalDateTime(props.endDate);

            // 모달 표시
            modalEl.show();
        }

    });

    calendar.render();
});


/////////////////////////////// 휴가 신청 눌렀을 때 동작하는 애
let initialTotalLeave = 0;      // 총 연차
let initialUsedLeave = 0;       // 이미 사용한 연차
let initialRemainingLeave = 0;  // 초기 남은 연차

document.getElementById("vacation-button-id").addEventListener("click", () => {
    fetch("/employee/vacationSelect")
        .then((res) => res.json())
        .then((data) => {
            if (data && data.length > 0) {

                const leaveInfo = data[0];

                // 초기 값 저장
                initialTotalLeave = leaveInfo.totalLeave;
                initialUsedLeave = leaveInfo.usedLeave;
                initialRemainingLeave = initialTotalLeave - initialUsedLeave;

                // 모달 초기 UI에 표시
                document.getElementById("allLeaveDay").value = initialTotalLeave.toFixed(1);
                document.getElementById("remainingDay").value = initialRemainingLeave.toFixed(1);
                document.getElementById("useDay").value = ''; // 사용 연차 초기화

                // 24-12-22에 결재자 추가로 때려박음
                const leaderSelect2 = document.getElementById("leader2"); //[241222 추가]
                leaderSelect2.innerHTML = "";// [241222 추가]

                data.forEach(item => {
                    const option2 = document.createElement("option");// [241222 추가]
                    option2.value = item.id; // 사번 또는 고유 ID [241222 추가]
                    option2.innerText = item.name; // 이름 표시 [241222 추가]
                    leaderSelect2.appendChild(option2); // 차일드로 박아 넣음 [241222 추가]
                });
            } else {
                alert("데이터를 불러오지 못했습니다.");
            }
        })
        .catch((error) => {
            console.error("데이터 로드 실패:", error);
        });

    const modalElement = new bootstrap.Modal(document.getElementById("vacationModal"), {});
    modalElement.show();
});

function getDateTime(divId) {
    const date = document.querySelector(`#${divId} input[type="date"]`).value;
    const time = document.querySelector(`#${divId} select`).value;
    if (date && time) {
        return new Date(`${date}T${time}`); // 'yyyy-MM-ddTHH:mm'
    }
    return null; // 값이 없으면 null 반환
}

document.getElementById("startDay").addEventListener("change", (event) => {
    const startDate = event.target.value;
    if (startDate) {
        document.getElementById("endDay").setAttribute("min", startDate);
    }
});

function calculateLeave() {
    const startDateTime = getDateTime("startDateTime");
    const endDateTime = getDateTime("endDateTime");

    if (!startDateTime || !endDateTime) {
        resetFields(); // 필드 초기화
        return;
    }

    if (endDateTime < startDateTime) {
        alert("종료일과 시간이 시작일과 시간보다 빠를 수 없습니다.");
        resetFields();
        return;
    }

    const diffInMs = endDateTime - startDateTime;
    const diffInDays = Math.floor(diffInMs / (1000 * 60 * 60 * 24)); // 날짜 차이
    const remainingHours = (diffInMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60); // 남은 시간

    let leaveDays = diffInDays; // 기본 날짜 수

    if (remainingHours > 0) {
        if (remainingHours <= 5) {
            leaveDays += 0.5; // 반차
        } else if (remainingHours <= 9) {
            leaveDays += 1.0; // 하루
        } else {
            leaveDays += Math.ceil(remainingHours / 9); // 9시간 단위로 추가
        }
    }

    const remainingLeave = initialRemainingLeave - leaveDays; // 남은 연차 계산
    if (remainingLeave < 0) {
        alert("사용 연차가 총 연차를 초과할 수 없습니다.");
        resetFields();
        return;
    }

    document.getElementById("useDay").value = leaveDays.toFixed(1);
    document.getElementById("remainingDay").value = remainingLeave.toFixed(1);
}


["startDay", "startTime", "endDay", "endTime"].forEach((id) => {
    document.getElementById(id).addEventListener("change", calculateLeave);
});


function resetFields() {
    document.getElementById("useDay").value = '';
    document.getElementById("remainingDay").value = initialRemainingLeave.toFixed(1);
}

// 과거 일자 선택 못함
function preventPastDate(inputId) {
    const today = new Date().toISOString().split("T")[0];
    document.getElementById(inputId).setAttribute("min", today);
}

preventPastDate("startDay");
preventPastDate("endDay");

//////////////////////////////////////////////////////////

//// 제출 버튼 누르면 서버에 데이터 저장하는애
document.getElementById("vacationApp").addEventListener("click", () => {

    // const name = document.getElementById("leader2").value;
    // const scheduleDate = document.getElementById("startDateTime").value;

    const startDay = document.getElementById("startDay").value;
    const startTime = document.getElementById("startTime").value || "00:00";

    const endDay = document.getElementById("endDay").value;
    const endTime = document.getElementById("endTime").value || "23:59";

    // `Date` 객체 생성
    const leaveStartDate = new Date(`${startDay}T${startTime}:00`);
    const leaveEndDate = new Date(`${endDay}T${endTime}:00`);

    // 9시간 빼기였는데 안 뺌
    const leaveStartAdjusted = new Date(leaveStartDate.getTime()); // 9시간을 밀리초로 계산해서 뺌
    const leaveEndAdjusted = new Date(leaveEndDate.getTime());
    console.log("leaveStartAdjusted : " + leaveStartAdjusted); // 얘가 알맞게 출력
    console.log("leaveEndAdjusted : " + leaveEndAdjusted); // 얘가 알맞게 출력

    // ISO 8601 형식으로 변환 (얘는 날짜 제대로 안 나오지만 이거 주석하면 제출이 안 됨)
    const leaveStartIso = leaveStartAdjusted.toISOString(); // 'yyyy-MM-ddTHH:mm:ssZ'
    const leaveEndIso = leaveEndAdjusted.toISOString(); // 'yyyy-MM-ddTHH:mm:ssZ'
    console.log("leaveStartIso : " + leaveStartIso);
    console.log("leaveEndIso : " + leaveEndIso);

    // 이렇게 ISO8601 형식으로 타입 맞춰줘야함
    // const leaveStartDate = `${startDay}T${startTime}`;
    // const leaveEndDate = `${endDay}T${endTime}`;

    // const usedLeave = parseFloat(document.getElementById("useDay").value);
    const usedLeave = parseFloat(document.getElementById("useDay").value);
    if (isNaN(usedLeave) || usedLeave <= 0) {
        alert("총 사용 개수를 확인하고 제출해주세요.");
        return;
    }

    // if (isNaN(usedLeave) || usedLeave <= 0) {
    //     alert("총 사용 개수를 확인하고 제출해주세요.");
    //     return;
    // }

    const leaveType = usedLeave === 0.5 ? 1 : 2;

    const vacationApplicationDTO = {
        // name: name,
        // scheduleStartDate: scheduleStartDate,
        // scheduleEndDate: scheduleEndDate,
        leaveStartDate: leaveStartIso,
        leaveEndDate: leaveEndIso,
        usedLeave: usedLeave,
        leaveType: leaveType, // 반차 구분 지으려고 추가
    }; // 서버로 보낼 데이터

    // 데이터 전송
    fetch("/employee/vacAppResult", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(vacationApplicationDTO),
    })
        .then(res => res.json()) // JSON 형태로 응답 파싱
        .then(data => {
            if (data.status === "success") {
                alert(data.message);
                location.reload(); // 페이지 새로고침으로 업데이트된 데이터 표시
            } else {
                alert(data.message);
            }
        })
        .catch(err => console.error("휴가 신청 실패:", err));

    // // 모달 닫기
    // const myModal = document.getElementById("myModal");
    // myModal.style.display = "none";
    // 모달 닫기
    const myModal = new bootstrap.Modal(document.getElementById("vacationModal"), {});
    myModal.hide();
});