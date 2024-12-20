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
document.getElementById("vacation-button-id").addEventListener("click", () => {
    fetch("/employee/vacationSelect")
        .then((res) => res.json())
        .then((data) => {
            if (data.length > 0) {
                const leaveInfo = data[0]; // 특정 사용자의 데이터 매핑
                document.getElementById("allLeaveDay").value = leaveInfo.totalLeave;
                document.getElementById("useDay").value = leaveInfo.usedLeave;
                document.getElementById("remainingDay").value = (leaveInfo.totalLeave - leaveInfo.usedLeave).toFixed(2);
            } else {
                alert("연차 데이터를 불러오지 못했습니다.");
            }
        });

    const modalElement = new bootstrap.Modal(document.getElementById("vacationModal"), {});
    modalElement.show();
});

function calculateLeave() {
    const startDate = new Date(document.getElementById("startDay").value);
    const endDate = new Date(document.getElementById("endDay").value);
    const allLeave = parseFloat(document.getElementById("allLeaveDay").value);
    const usedLeave = parseFloat(document.getElementById("useDay").value) || 0;

    // 시작/종료 날짜와 시간이 유효해야 함
    if (startDate && endDate && startDate <= endDate) {
        const startHour = startDate.getHours();
        const endHour = endDate.getHours();

        let leaveDays = 0;

        if (
            (startHour === 9 || startHour === 14) && // 시작시간이 09:00 또는 14:00
            (endHour === 14 || endHour === 18) // 종료시간이 14:00 또는 18:00
        ) {
            const diffInMs = endDate - startDate; // 두 시간의 차이 (밀리초)
            const diffInHours = diffInMs / (1000 * 60 * 60); // 차이를 시간 단위로 계산

            if (diffInHours <= 4) {
                leaveDays = 0.5; // 반차
            } else if (diffInHours <= 9) {
                leaveDays = 1; // 하루 휴가
            } else {
                leaveDays = Math.ceil(diffInHours / 9); // 하루 단위 이상
            }

            document.getElementById("useDay").value = leaveDays.toFixed(1);
            document.getElementById("remainingDay").value = (allLeave - (usedLeave + leaveDays)).toFixed(2);
        } else {
            alert("시작/종료 시간은 지정된 시간만 선택 가능합니다. (시작: 09:00/14:00, 종료: 14:00/18:00)");
            document.getElementById("useDay").value = '';
            document.getElementById("remainingDay").value = allLeave - usedLeave;
        }
    } else {
        document.getElementById("useDay").value = '';
        document.getElementById("remainingDay").value = allLeave - usedLeave;
    }
}

document.getElementById("startDay").addEventListener("change", calculateLeave);
document.getElementById("endDay").addEventListener("change", calculateLeave);

// 시작/종료 시간 선택 제한
document.getElementById("startDay").addEventListener("change", () => {
    const startTimeInput = document.getElementById("startDay");
    const startTime = new Date(startTimeInput.value);
    const hour = startTime.getHours();

    if (hour !== 9 && hour !== 14) {
        alert("시작 시간은 09:00 또는 14:00만 선택 가능합니다.");
        startTimeInput.value = '';
    }
});

document.getElementById("endDay").addEventListener("change", () => {
    const endTimeInput = document.getElementById("endDay");
    const endTime = new Date(endTimeInput.value);
    const hour = endTime.getHours();

    if (hour !== 14 && hour !== 18) {
        alert("종료 시간은 14:00 또는 18:00만 선택 가능합니다.");
        endTimeInput.value = '';
    }
});


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


// const result = {
//     allLeaveDay: document.getElementById("allLeaveDay").textContent,
//     startDay: document.getElementById("startDay").textContent,
//     endDay: document.getElementById("endDay").textContent,
//     useDay: document.getElementById("useDay").textContent,
//     remainingDay: document.getElementById("remainingDay").textContent
// };


// document.addEventListener('DOMContentLoaded', function () {
//     const calendarEl = document.getElementById('calendar');
//     const modalEl = document.getElementById('eventModal'); // 모달 엘리먼트
//     const calendar = new FullCalendar.Calendar(calendarEl, {
//         headerToolbar: {
//             left: 'prev,next today',
//             center: 'title',
//             right: 'dayGridMonth,dayGridWeek,dayGridDay'
//         },
//         initialDate: '2024-12-17',
//         navLinks: true,
//         editable: false,
//         dayMaxEvents: true, // "더보기" 링크
//
//         events: function (fetchInfo, successCallback, failureCallback) {
//             fetch('/api/calendar')
//                 .then((response) => {
//                     if (!response.ok) {
//                         throw new Error(`HTTP error! status: ${response.status}`);
//                     }
//                     return response.json();
//                 })
//                 .then((data) => {
//                     const result = data.cal || data; // 응답 구조에 따라 "cal" 키 확인
//                     if (!Array.isArray(result)) {
//                         throw new TypeError('Expected result to be an array');
//                     }
//
//                     const events = [];
//                     const seenDates = new Set(); // 중복 일정 처리용 Set
//
//                     result.forEach((item) => {
//                         const name = item.employeeDTO?.name || 'Unknown'; // 이름
//                         const position = item.humanDTO?.position || ''; // 직급
//                         const workOff = item.commuteDTO?.workOff; // 퇴근 시간
//                         const scheduleDate = item.scheduleDTO?.scheduleDate; // 기타 일정 날짜
//                         const leaveDate = item.dayOffDTO?.leaveDate; // 휴가 날짜
//                         const leaveType = item.dayOffDTO?.leaveType; // 휴가 타입
//
//                         // 1. 연차/반차 처리
//                         if (leaveDate && leaveType !== undefined) {
//                             if (leaveType === 2 && !seenDates.has(leaveDate)) {
//                                 // 연차
//                                 events.push({
//                                     title: `${name} ${position} (연차)`,
//                                     start: leaveDate,
//                                     backgroundColor: 'green',
//                                     extendedProps: {
//                                         type: '연차',
//                                         memo: item.dayOffDTO?.leaveMemo,
//                                         startDate: leaveDate,
//                                         endDate: leaveDate
//                                     }
//                                 });
//                                 seenDates.add(leaveDate);
//                             } else if (leaveType === 1 && !seenDates.has(leaveDate)) {
//                                 // 반차
//                                 events.push({
//                                     title: `${name} ${position} (반차)`,
//                                     start: leaveDate,
//                                     backgroundColor: 'lightgreen',
//                                     extendedProps: {
//                                         type: '반차',
//                                         memo: item.dayOffDTO?.leaveMemo,
//                                         startDate: leaveDate,
//                                         endDate: leaveDate
//                                     }
//                                 });
//                                 seenDates.add(leaveDate);
//                             }
//                         }
//
//                         // 2. 초과근무 처리
//                         if (workOff && scheduleDate && workOff.slice(11, 16) > "18:00" && !seenDates.has(scheduleDate)) {
//                             events.push({
//                                 title: `${name} ${position} (연장근무)`,
//                                 start: scheduleDate,
//                                 backgroundColor: 'orange',
//                                 extendedProps: {
//                                     type: '연장근무',
//                                     workOff,
//                                     startDate: scheduleDate,
//                                     endDate: scheduleDate
//                                 }
//                             });
//                             seenDates.add(scheduleDate);
//                         }
//                     });
//
//                     successCallback(events); // FullCalendar에 이벤트 전달
//                 })
//                 .catch((error) => {
//                     console.error('Error fetching calendar events:', error);
//                     failureCallback(error);
//                 });
//         },
//         eventClick: function (info) {
//             // 이벤트 클릭 시 동작
//             const props = info.event.extendedProps;
//             modalEl.querySelector('h3').textContent = '일정 조회';
//             modalEl.querySelector('p:nth-of-type(1)').textContent = `구 분 : ${props.type}`;
//             modalEl.querySelector('p:nth-of-type(2)').textContent = `시작일 : ${props.startDate}`;
//             modalEl.querySelector('p:nth-of-type(3)').textContent = `종료일 : ${props.endDate}`;
//             modalEl.querySelector('p:nth-of-type(4)').textContent = `메모 : ${props.memo || '없음'}`;
//             modalEl.style.display = 'block'; // 모달 표시
//         }
//     });
//
//     // 모달 닫기
//     modalEl.addEventListener('click', function () {
//         modalEl.style.display = 'none'; // 모달 숨김
//     });
//
//     calendar.render();
// });
