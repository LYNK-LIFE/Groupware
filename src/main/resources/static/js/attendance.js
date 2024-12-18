// 기본 달력 동작 (일 / 월 등등...)

document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,dayGridWeek,dayGridDay'
        },
        initialDate: '2024-12-17',
        navLinks: true,
        editable: false,
        dayMaxEvents: true,

        events: function (fetchInfo, successCallback, failureCallback) {
            fetch('/api/calendar')
                .then(response => response.json())
                .then(data => {
                    const events = data.map(item => {
                        // 데이터 추출
                        const name = item.employeeDTO.name || 'Unknown';    // 이름
                        const position = item.humanDTO.position || '';     // 직급
                        const workOff = item.commuteDTO.workOff;           // 퇴근 시간
                        const leaveDate = item.leave_date;                 // 연차/반차 날짜
                        const leaveType = item.leave_type;                 // 연차/반차 타입
                        const scheduleDate = item.scheduleDTO.scheduleDate; // 기타 일정 날짜

                        // 기본 값
                        let title = `${name} ${position}`;
                        let backgroundColor = 'gray';  // 기본 색상
                        let startDate = scheduleDate;  // 시작 날짜 (기본 일정)

                        // 조건 처리
                        if (leaveType === 2) { // 연차
                            backgroundColor = 'green';
                            startDate = leaveDate;
                        } else if (leaveType === 1) { // 반차
                            backgroundColor = 'lightgreen';
                            startDate = leaveDate;
                        } else if (workOff && workOff.slice(11, 16) > "18:00") { // 초과근무
                            backgroundColor = 'orange';
                            startDate = scheduleDate;
                        }

                        return {
                            title,
                            start: startDate,          // 일정 시작 날짜
                            backgroundColor,           // 색상
                            extendedProps: {           // 추가 데이터
                                name,
                                position,
                                workOff,
                                leaveDate,
                                leaveType,
                                scheduleType: item.scheduleDTO.scheduleType
                            }
                        };
                    });

                    successCallback(events); // FullCalendar로 이벤트 전달
                })
                .catch(error => {
                    console.error('Error fetching calendar events:', error);
                    failureCallback(error);
                });
        }
    });

    calendar.render();
});




// document.addEventListener('DOMContentLoaded', function () {
//     var calendarEl = document.getElementById('calendar');
//
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         headerToolbar: {
//             left: 'prev,next today',      // 이전/다음/오늘 버튼
//             center: 'title',             // 타이틀
//             right: 'dayGridMonth,dayGridWeek,dayGridDay' // 달력 보기 옵션
//         },
//         initialDate: '2024-12-17',        // 초기 날짜
//         navLinks: true,                   // 날짜 클릭 가능
//         editable: false,                  // 드래그/수정 비활성화
//         dayMaxEvents: true,               // "더보기" 링크
//         events: function (fetchInfo, successCallback, failureCallback) {
//             fetch('/api/calendar') // Controller에서 데이터 가져오기
//                 .then(response => response.json())
//                 .then(data => {
//                     const events = data.map(item => {
//                         // 필요한 데이터만 추출하여 가공
//                         const name = item.employeeDTO.name;             // 이름
//                         const position = item.humanDTO.position || '';  // 직급
//                         const workOff = item.commuteDTO.workOff;        // 퇴근 시간
//                         const scheduleDate = item.scheduleDTO.scheduleDate; // 일정 날짜
//                         const isOvertime = workOff && workOff.slice(11, 16) > "18:00"; // 초과근무 확인
//
//                         return {
//                             title: `${name} ${position}`,               // 제목: 이름 + 직급
//                             start: scheduleDate,                        // 시작 날짜
//                             backgroundColor: isOvertime
//                                 ? 'orange'                              // 초과근무: 주황색
//                                 : 'green',                              // 연차: 녹색
//                             extendedProps: {                            // 추가 데이터
//                                 name,
//                                 position,
//                                 workOff,
//                                 totalLeave: item.humanDTO.totalLeave,
//                                 usedLeave: item.humanDTO.usedLeave,
//                                 scheduleType: item.scheduleDTO.scheduleType,
//                                 scheduleNote: item.scheduleDTO.scheduleNote
//                             }
//                         };
//                     });
//
//                     successCallback(events); // FullCalendar로 이벤트 전달
//                 })
//                 .catch(error => {
//                     console.error('Error fetching calendar data:', error);
//                     failureCallback(error);
//                 });
//         }
//     });
//     calendar.render();
// });



// document.addEventListener('DOMContentLoaded', function () {
//     var calendarEl = document.getElementById('calendar');
//
//     // FullCalendar 초기화
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         headerToolbar: {
//             left: 'prevYear,prev,next,nextYear today',
//             center: 'title',
//             right: 'dayGridMonth,dayGridWeek,dayGridDay'
//         },
//         initialDate: '2024-12-17',
//         navLinks: true,    // 날짜 클릭 가능
//         editable: true,    // 드래그 가능 여부
//         dayMaxEvents: true, // "더보기" 링크 표시
//
//         events: function (fetchInfo, successCallback, failureCallback) {
//             fetch('/api/calendar')
//                 .then(response => response.json())
//                 .then(data => {
//                     const events = data.map(item => {
//                         const isOvertime = item.commuteDTO.workOff > "18:00:00"; // 초과근무 여부 확인
//                         const isLeave = item.humanDTO.totalLeave && item.humanDTO.usedLeave; // 연차 여부 확인
//
//                         return {
//                             title: `${item.employeeDTO.name} ${item.employeeDTO.position}`, // 이름 + 직급
//                             start: item.scheduleDTO.scheduleDate, // 일정 시작
//                             backgroundColor: isOvertime ? 'orange' : (isLeave ? 'green' : 'gray'), // 조건부 색상 설정
//                             extendedProps: { // 상세 정보를 저장
//                                 type: isOvertime ? '초과근무' : '연차',
//                                 name: item.employeeDTO.name,
//                                 position: item.employeeDTO.position,
//                                 workDate: item.commuteDTO.workDate,
//                                 workOn: item.commuteDTO.workOn,
//                                 workOff: item.commuteDTO.workOff,
//                                 totalLeave: item.humanDTO.totalLeave,
//                                 usedLeave: item.humanDTO.usedLeave,
//                             }
//                         };
//                     });
//                     successCallback(events);
//                 })
//                 .catch(error => {
//                     console.error('Error fetching events:', error);
//                     failureCallback(error);
//                 });
//         },
//
//         // 이벤트 클릭 시 모달창 표시
//         eventClick: function (info) {
//             const props = info.event.extendedProps;
//
//             // 모달 내용 채우기
//             document.getElementById('modal-type').innerText = props.type; // 구분
//             document.getElementById('modal-name').innerText = props.name; // 이름
//             document.getElementById('modal-position').innerText = props.position; // 직급
//             document.getElementById('modal-workdate').innerText = props.workDate; // 근무일시
//             document.getElementById('modal-endtime').innerText = props.workOff || 'N/A'; // 종료일시
//             if (props.type === '초과근무') {
//                 document.getElementById('modal-extra-hours').innerText = calculateOvertime(props.workOn, props.workOff); // 초과근무시간
//                 document.getElementById('modal-leave-time').innerText = '-'; // 연차는 표시하지 않음
//             } else if (props.type === '연차') {
//                 document.getElementById('modal-leave-time').innerText = `${props.usedLeave}/${props.totalLeave}`; // 사용 연차 / 총 연차
//                 document.getElementById('modal-extra-hours').innerText = '-'; // 초과근무는 표시하지 않음
//             }
//
//             // 모달 표시
//             const modal = document.getElementById('eventModal');
//             modal.style.display = 'block';
//         }
//     });
//
//     calendar.render();
//
//     // 모달 닫기 버튼
//     document.getElementById('modal-close').addEventListener('click', function () {
//         const modal = document.getElementById('eventModal');
//         modal.style.display = 'none';
//     });
//
//     // 초과근무시간 계산 함수
//     function calculateOvertime(start, end) {
//         if (!start || !end) return 'N/A';
//         const startTime = new Date(`1970-01-01T${start}`);
//         const endTime = new Date(`1970-01-01T${end}`);
//         const diffMs = endTime - startTime;
//
//         // 차이 계산 (밀리초 -> 시간)
//         const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
//         const diffMinutes = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));
//         return `${diffHours}시간 ${diffMinutes}분`;
//     }
// });
