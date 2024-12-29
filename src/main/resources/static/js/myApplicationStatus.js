document.addEventListener("DOMContentLoaded", () => {
    let allEmployees = [];
    const lookupSelect = document.getElementById("lookupSelect2");
    const lookupInput = document.getElementById("lookupInput2");
    const tableBody = document.getElementById("employee-table-body2");

    // 페이지 로드 시 데이터 요청
    fetch("/employee/appStatusList")
        .then(res => res.json())
        .then(data => {
            allEmployees = data; // 데이터 저장
            renderTable(allEmployees); // 테이블 출력
            console.log("데이터 로드 성공:", allEmployees);
        })
        .catch(err => console.error("데이터 로드 실패:", err));

    // 테이블 데이터 렌더링 함수
    function renderTable(data) {
        tableBody.innerHTML = "";
        if (data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
            return;
        }

        data.forEach(item => {
            const row = document.createElement("tr");

            // 별도 추가하기!!! (클릭 했을 때 가져온 데이터를 고대로 넣는 게 아니라서!!)
            row.setAttribute("data-leader" , item.employeeDTO.name);

            // 연장 근무 신청시 데이터 가져오기 위한 설정 (담당자↑, 시작시간(근무일시)↓, 연장 근로시간↓)
            // row.setAttribute("data-start-over-day" , item.startOverTime || "N/A");
            // row.setAttribute("data-total-over-time", item.totalOverTime || "N/A");
            row.setAttribute("data-start-over-day", item.scheduleDTO?.scheduleStartDate || "N/A");
            row.setAttribute("data-total-over-time", item.totalOverTime || "N/A");

            // 상태별 CSS 클래스 추가
            const statusClass = getStatusClass(item.draftshDTO.draftState);
            if (statusClass) {
                row.classList.add(statusClass); // CSS 클래스 추가
            }

            // 구분값 설정
              row.innerHTML = `
            <td>${getStatusLabel(item.draftshDTO.draftState)}</td>
             <td>${getLeaveTypeDescription(item.dayOffDTO.leaveType)}</td> <!-- 숫자 -> 텍스트 변환 -->
            <td>${item.departmentDTO?.depName || "N/A"}</td>
            <td>${item.employeeDTO?.name || "N/A"}</td>
            <td>${item.humanDTO?.position || "N/A"}</td>
            <td>${formatDate(item.draftshDTO.draftDate)}</td>
            <td>${formatDate(item.draftshDTO.draftCompletionTime)}</td>
        `;
            tableBody.appendChild(row);
        });
    }

    function getLeaveTypeDescription(leaveType) {
        switch (leaveType) {
            case 1: return "반차";
            case 2: return "연차";
            case 0: return "연장근무";
            default: return "알 수 없음";
        }
    }


    function performSearch2() {
        const type = lookupSelect.value; // 검색 타입
        const keyword = lookupInput.value.trim(); // 입력 키워드

        if (!keyword) {
            alert("검색어를 입력해 주세요.");
            return;
        }

        const filteredData = allEmployees.filter(item => {
            if (type === "상태") {
                return getStatusLabel(item.draftshDTO.draftState).includes(keyword); // 상태 필터
            } else if (type === "구분") {
                return item.dayOffDTO.leaveType.includes(keyword); // 구분 필터
            } else if (type === "이름") {
                return item.employeeDTO?.name.includes(keyword); // 이름 필터
            }
            return false;
        });

        renderTable(filteredData);
        lookupInput.value = ""; // 입력 초기화
    }


// 검색 버튼 클릭 이벤트
    document.getElementById("select-button-id").addEventListener("click", performSearch2);

// Enter 키 이벤트 추가 (검색 입력창)
    document.getElementById("lookupInput2").addEventListener("keyup", (event) => {
        if (event.key === "Enter") {
            performSearch2();
        }
    });

    // 상태 라벨 반환
    function getStatusLabel(status) {
        if (status === 0) return "대기";
        if (status === 1) return "확인";
        if (status === 2) return "승인";
        if (status === 9) return "반려";
        return "알 수 없음";
    }

    // 상태별 CSS 클래스 반환
    function getStatusClass(status) {
        if (status === 0) return "status-waiting"; // 대기
        if (status === 1) return "status-waiting"; // 확인
        if (status === 2) return "status-approved"; // 승인
        if (status === 9) return "status-rejected"; // 반려
        return null;
    }

    // 날짜 포맷 함수
    function formatDate(datetime) {
        if (!datetime) return "N/A";
        const date = new Date(datetime);
        const yyyy = date.getFullYear();
        const mm = String(date.getMonth() + 1).padStart(2, "0");
        const dd = String(date.getDate()).padStart(2, "0");
        const hh = String(date.getHours()).padStart(2, "0");
        const mi = String(date.getMinutes()).padStart(2, "0");
        return `${yyyy}-${mm}-${dd} ${hh}:${mi}`;
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
