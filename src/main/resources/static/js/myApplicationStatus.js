document.addEventListener("DOMContentLoaded", () => {
    let allEmployees = [];
    const tableBody = document.getElementById("employee-table-body2");

    // 데이터 요청
    fetch("/employee/appStatusList")
        .then(res => res.json())
        .then(data => {
            allEmployees = data; // 데이터 저장
            renderTable(allEmployees); // 테이블 출력
            console.log("데이터 로드 성공:", allEmployees);
        })
        .catch(err => console.error("데이터 로드 실패:", err));

    // 테이블 데이터 렌더링
    function renderTable(data) {
        tableBody.innerHTML = "";
        if (data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
            return;
        }

        data.forEach(item => {
            const row = document.createElement("tr");

            // 구분 처리 (leaveType -> 반차/연차 or 연장근무)
            const leaveTypeDescription = item.dayOffDTO
                ? getLeaveTypeDescription(item.dayOffDTO.leaveType) // 휴가/반차
                : "연장근무"; // 연장근무

            const statusClass = getStatusClass(item?.draftshDTO?.draftState ?? null);
            if (statusClass) {
                row.classList.add(statusClass);
            }

            row.innerHTML = `
                <td>${getStatusLabel(item?.draftshDTO?.draftState ?? "N/A")}</td>
                <td>${leaveTypeDescription}</td> <!-- 구분 표시 -->
                <td>${item?.departmentDTO?.depName || "N/A"}</td>
                <td>${item?.employeeDTO?.name || "N/A"}</td>
                <td>${item?.humanDTO?.position || "N/A"}</td>
                <td>${formatDate(item?.draftshDTO?.draftDate)}</td>
                <td>${formatDate(item?.draftshDTO?.draftCompletionTime)}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    // leaveType에 따른 설명 반환
    function getLeaveTypeDescription(leaveType) {
        switch (leaveType) {
            case 1: return "반차";
            case 2: return "연차";
            default: return "알 수 없음";
        }
    }

    // draftState에 따른 라벨 반환
    function getStatusLabel(status) {
        switch (status) {
            case 0: return "미확인";
            case 1: return "확인";
            case 2: return "결재";
            case 9: return "반려";
            default: return "알 수 없음";
        }
    }

    // 상태별 CSS 클래스 반환
    function getStatusClass(status) {
        if (status === 0) return "status-confirmed";
        if (status === 1) return "status-waiting";
        if (status === 2) return "status-approved";
        if (status === 9) return "status-rejected";
        return null;
    }

    // 날짜 포맷 함수
    function formatDate(datetime) {
        if (!datetime) return "N/A";
        const date = new Date(datetime);
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(2, "0")} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
    }
});



// 클릭 시 상세 조회 모달창
document.getElementById("employee-table-body2").addEventListener("click" , (event) => {
    const row = event.target.closest("tr");

    if (row) {
        // const cells = document.getElementsByTagName("td"); 이러면 첫번째 행만 가져옴!!
        const cells = row.getElementsByTagName("td");
        // 이 한 줄 차이로 조회가 잘 되나 하나만 되나~ 그게 갈림

        // 담당자 별도 추가하기!
        // const leader = row.getAttribute("data-leader");
        //
        // const startOverTime = row.getAttribute("data-start-over-day");
        // const totalOverTime = row.getAttribute("data-total-over-time");
        //
        // document.getElementById("status").value = cells[0].textContent;
        //
        // // 별도로 꺼내줌 (얘 나중에 바꿔 줘야함. 지금은 지 이름 돼있음)
        // document.getElementById("leader").value = leader || "";
        // // 연장 근무 신청의 데이터를 나의 현황 페이지 상세 조회에서 확인 하게 별도 꺼내기
        //
        // // 얘네는 아직 못 불러오고 있음 나중에 수정해야 함.
        // document.getElementById("overTime").value = `${totalOverTime || "N/A"} 시간`;
        // document.getElementById("workTime").value = `${startOverTime || "N/A"}`;

        document.getElementById("status").value = cells[0].textContent; // 상태
        document.getElementById("leader").value = row.getAttribute("data-leader") || "N/A"; // 담당자
        document.getElementById("position").value = cells[4].textContent; // 직책
        document.getElementById("applicationOverTime").value = cells[5].textContent; // 신청 일시
        document.getElementById("approvalOverTime").value = cells[6].textContent; // 승인 일시

        // 연장근무 관련 데이터 추가
        document.getElementById("overTime").value = `${row.getAttribute("data-total-over-time") || "N/A"} 시간`; // 연장 근로 시간
        document.getElementById("workTime").value = row.getAttribute("data-start-over-day") || "N/A"; // 근무 시작 시간
    }

    const modalElement = new bootstrap.Modal(document.getElementById("myModal3"), {});
    modalElement.show();

});

// 연장 근무 시간 계산

// 이거 이제 해야 하3!!!! 추가로, 연차 / 연장 근무 별로 조회 모달 따로...
                        // 연장 근무는 계산만 하면 되3.
