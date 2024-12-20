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
                        const uniqueKey = `${item.employeeDTO?.name}-${item.scheduleDTO?.scheduleDate}-${item.dayOffDTO?.leaveType}`;
                        // 각 일정의 고유 키 생성

                        if (!seenEvents.has(uniqueKey)) { // 중복 확인
                            events.push({
                                title: `${item.employeeDTO?.name || 'Unknown'} ${item.humanDTO?.position || ''}`,
                                start: item.scheduleDTO?.scheduleDate || item.dayOffDTO?.leaveDate,
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
                                    startDate: item.scheduleDTO?.scheduleDate || item.dayOffDTO?.leaveDate,
                                    endDate: item.scheduleDTO?.scheduleDate || item.dayOffDTO?.leaveDate
                                }
                            });
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

            // 모달 내용 업데이트
            document.getElementById('eventType').textContent = props.type || '없음';
            document.getElementById('eventName').textContent = props.name || '없음';
            document.getElementById('eventDepartment').textContent = props.department || '없음';
            document.getElementById('eventPosition').textContent = props.position || '없음';
            document.getElementById('eventStart').textContent = props.startDate || 'N/A';
            document.getElementById('eventEnd').textContent = props.endDate || 'N/A';
            // document.getElementById('eventMemo').textContent = props.memo || '없음';

            // Bootstrap 모달 표시
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
    const payload = {
        totalLeave: parseFloat(document.getElementById("allLeaveDay").value),
        startDay: document.getElementById("startDay").value,
        endDay: document.getElementById("endDay").value,
        usedLeave: parseFloat(document.getElementById("useDay").value),
        remainingLeave: parseFloat(document.getElementById("remainingDay").value)
    };

    fetch("employee/vacAppResult", { // 이거로 데이터 보내주고
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    })
        .then(response => response.json())
        .then(data => { // 다시 받아와서 성공 , 실패 여부 반환
            if (data.vacStatus === "success") {
                alert("휴가 신청이 성공적으로 저장되었습니다!");
                location.reload(); // 페이지를 새로고침하여 업데이트된 정보 반영
            } else {
                alert("휴가 신청에 실패했습니다. 다시 시도해주세요.");
            }
        });
});