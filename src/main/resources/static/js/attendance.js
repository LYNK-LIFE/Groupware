document.addEventListener('DOMContentLoaded', function () {
    const calendarEl = document.getElementById('calendar');

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
                    const result = data.cal || data; // 응답 구조에 따라 "cal" 키 확인
                    if (!Array.isArray(result)) {
                        throw new TypeError('Expected result to be an array');
                    }

                    const events = [];
                    const seenDates = new Set(); // 중복 일정 처리용 Set

                    result.forEach((item) => {
                        const name = item.employeeDTO?.name || 'Unknown'; // 이름
                        const position = item.humanDTO?.position || ''; // 직급
                        const workOff = item.commuteDTO?.workOff; // 퇴근 시간
                        const scheduleDate = item.scheduleDTO?.scheduleDate; // 기타 일정 날짜
                        const leaveDate = item.dayOffDTO?.leaveDate; // 휴가 날짜
                        const leaveType = item.dayOffDTO?.leaveType; // 휴가 타입

                        // 1. 연차/반차 처리
                        if (leaveDate && leaveType !== undefined) {
                            if (leaveType === 2 && !seenDates.has(leaveDate)) {
                                // 연차
                                events.push({
                                    title: `${name} ${position} (연차)`,
                                    start: leaveDate,
                                    backgroundColor: 'green',
                                    extendedProps: { type: '연차', memo: item.dayOffDTO?.leaveMemo }
                                });
                                seenDates.add(leaveDate);
                            } else if (leaveType === 1 && !seenDates.has(leaveDate)) {
                                // 반차
                                events.push({
                                    title: `${name} ${position} (반차)`,
                                    start: leaveDate,
                                    backgroundColor: 'lightgreen',
                                    extendedProps: { type: '반차', memo: item.dayOffDTO?.leaveMemo }
                                });
                                seenDates.add(leaveDate);
                            }
                        }

                        // 2. 초과근무 처리
                        if (workOff && scheduleDate && workOff.slice(11, 16) > "18:00" && !seenDates.has(scheduleDate)) {
                            events.push({
                                title: `${name} ${position} (초과근무)`,
                                start: scheduleDate,
                                backgroundColor: 'orange',
                                extendedProps: { type: '초과근무', workOff }
                            });
                            seenDates.add(scheduleDate);
                        }
                    });

                    successCallback(events); // FullCalendar에 이벤트 전달
                })
                .catch((error) => {
                    console.error('Error fetching calendar events:', error);
                    failureCallback(error);
                });
        }
    });

    calendar.render();
});
