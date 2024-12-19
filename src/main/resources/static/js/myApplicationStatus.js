document.addEventListener("DOMContentLoaded", () => {
    let allEmployees = []; // 데이터를 저장할 변수
    const lookupSelect = document.getElementById("lookupSelect2");
    const lookupInput = document.getElementById("lookupInput2");
    const tableBody = document.getElementById("employee-table-body2");

    // 페이지 로드 시 데이터 요청
    fetch("/employee/appStatusList")
        .then(res => res.json())
        .then(data => {
            // 중복 제거: Map을 사용해 id 기준 중복 제거
            const uniqueData = Array.from(new Map(data.map(item => [item.id, item])).values());
            allEmployees = uniqueData; // 중복 제거된 데이터 저장
            renderTable(allEmployees); // 테이블 출력
            console.log("중복 제거 후 데이터:", allEmployees);
        })
        .catch(err => console.error("데이터 로드 실패:", err));

    // 테이블 데이터 렌더링 함수
    function renderTable(data) {
        tableBody.innerHTML = ""; // 테이블 초기화
        if (data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
            return;
        }

        data.forEach(item => {
            const row = document.createElement("tr");

            // 상태별 배경색 추가
            const statusClass = getStatusClass(item.approver);
            row.className = statusClass;

            row.innerHTML = `
                <td>${getStatusLabel(item.approver)}</td>
                <td>${getScheduleType(item.scheduleDTO?.scheduleType)}</td>
                <td>${item.departmentDTO?.depName || 'N/A'}</td>
                <td>${item.employeeDTO?.name || 'N/A'}</td>
                <td>${item.humanDTO?.position || 'N/A'}</td>
                <td>${item.draftTime ? formatDate(item.draftTime) : 'N/A'}</td>
                <td>${item.approveTime ? formatDate(item.approveTime) : 'N/A'}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    // 검색 버튼 클릭 시 동작
    document.getElementById("select-button-id").addEventListener("click", () => {
        const type = lookupSelect.value; // 선택된 필터 타입
        const keyword = lookupInput.value.trim(); // 검색 키워드

        if (!keyword) {
            alert("검색어를 입력해 주세요.");
            return;
        }

        const filtered = allEmployees.filter(item => {
            if (type === "휴가") {
                // 연차 관련 데이터만 필터링
                return (
                    (item.scheduleDTO?.scheduleType === 1) && // 부서 일정
                    item.humanDTO?.name.includes(keyword)
                );
            } else if (type === "연장근무") {
                // 연장 근무 필터링
                return (
                    item.commuteDTO?.workOff &&
                    item.commuteDTO.workOff > "18:00" && // 초과 근무 조건
                    item.humanDTO?.name.includes(keyword)
                );
            }
            return false;
        });

        renderTable(filtered); // 결과 출력
        lookupInput.value = ""; // 입력 초기화
    });

    // 상태 라벨 반환 함수
    function getStatusLabel(status) {
        if (status === 0) return "대기중";
        if (status === 1) return "승인됨";
        if (status === 2) return "반려됨";
        return "알 수 없음";
    }

    // 일정 타입 라벨 반환 함수
    function getScheduleType(scheduleType) {
        if (scheduleType === 0) return "본인 일정";
        if (scheduleType === 1) return "부서 일정";
        if (scheduleType === 2) return "전사 일정";
        return "알 수 없음";
    }

    // 상태별 클래스 반환 함수
    function getStatusClass(status) {
        if (status === 0) return "status-waiting"; // 파랑
        if (status === 1) return "status-approved"; // 연두색
        if (status === 2) return "status-rejected"; // 빨강
        return ""; // 기본값
    }

    // 날짜 포맷 함수
    function formatDate(datetime) {
        const date = new Date(datetime);
        const yyyy = date.getFullYear();
        const mm = String(date.getMonth() + 1).padStart(2, "0");
        const dd = String(date.getDate()).padStart(2, "0");
        const hh = String(date.getHours()).padStart(2, "0");
        const mi = String(date.getMinutes()).padStart(2, "0");
        return `${yyyy}-${mm}-${dd} ${hh}:${mi}`;
    }
});
