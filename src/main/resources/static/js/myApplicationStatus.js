document.addEventListener("DOMContentLoaded", () => {
    let allEmployees = [];
    const tableBody = document.getElementById("employee-table-body2");
    const lookupSelect = document.getElementById("lookupSelect2");
    const lookupInput = document.getElementById("lookupInput2");
    const searchButton = document.getElementById("select-button-id");

    // 데이터 요청
    fetch("/employee/appStatusList")
        .then(res => res.json())
        .then(data => {
            allEmployees = data; // 데이터 저장
            renderTable(allEmployees); // 테이블 출력
            console.log("데이터 로드 성공:", allEmployees);
        })
        .catch(err => console.error("데이터 로드 실패:", err));

    // 검색 버튼 클릭 및 엔터 키 입력 이벤트
    searchButton.addEventListener("click", filterAndRender);
    lookupInput.addEventListener("keyup", (e) => {
        if (e.key === "Enter") {
            filterAndRender();
        }
    });

    // 테이블 데이터 렌더링
    function renderTable(data) {
        tableBody.innerHTML = "";
        if (data.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
            return;
        }

        data.forEach(item => {
            const row = document.createElement("tr");

            const leaveTypeDescription = item.dayOffDTO
                ? getLeaveTypeDescription(item.dayOffDTO.leaveType)
                : "연장근무";

            const statusClass = getStatusClass(item?.draftshDTO?.draftState ?? null);
            if (statusClass) {
                row.classList.add(statusClass);
            }

            row.setAttribute("data-draft-no", item.draftshDTO?.draftNo || "");
            // select 구문에 있어야 함.....

            row.setAttribute("data-leader", item.employeeDTO?.name || "N/A");
            row.setAttribute(
                "data-total-over-time",
                calculateTotalOverTime(
                    item.scheduleDTO?.scheduleStartDate,
                    item.scheduleDTO?.scheduleEndDate
                )
            );
            row.setAttribute("data-start-over-day", item.scheduleDTO?.scheduleStartDate || "N/A");
            row.setAttribute("data-vac-start", item.dayOffDTO?.leaveStartDate || "N/A");
            row.setAttribute("data-vac-end", item.dayOffDTO?.leaveEndDate || "N/A");

            row.innerHTML = `
        <td>${getStatusLabel(item?.draftshDTO?.draftState ?? "N/A")}</td>
        <td>${leaveTypeDescription}</td>
        <td>${item?.departmentDTO?.depName || "N/A"}</td>
        <td>${item?.employeeDTO?.name || "N/A"}</td>
        <td>${item?.humanDTO?.position || "N/A"}</td>
        <td>${formatDate(item?.draftshDTO?.draftDate)}</td>
        <td>${formatDate(item?.draftshDTO?.draftCompletionTime)}</td>
    `;
            tableBody.appendChild(row);
        });
    }

    // 검색 및 필터링
    function filterAndRender() {
        const criteria = lookupSelect.value; // 검색 기준 (상태/구분/이름)
        const query = lookupInput.value.trim().toLowerCase(); // 검색어 (소문자 변환)

        const filteredData = allEmployees.filter(item => {
            switch (criteria) {
                case "상태":
                    return getStatusLabel(item?.draftshDTO?.draftState ?? "").toLowerCase().includes(query);
                case "구분":
                    const leaveTypeDescription = item.dayOffDTO
                        ? getLeaveTypeDescription(item.dayOffDTO.leaveType)
                        : "연장근무";
                    return leaveTypeDescription.toLowerCase().includes(query);
                case "이름":
                    return (item?.employeeDTO?.name || "").toLowerCase().includes(query);
                default:
                    return true;
            }
        });

        renderTable(filteredData); // 필터링된 데이터를 렌더링
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
            case 0: return "-";
            case 1: return "확인";
            case 2: return "승인";
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

// 클릭 했을 때 연장 근무 시간 계산
function calculateTotalOverTime(startDate, endDate) {
    if (!startDate || !endDate) return "N/A"; // 날짜가 없으면 N/A 반환

    const start = new Date(startDate); // 시작 시간
    const end = new Date(endDate);     // 종료 시간

    const diffInMs = end - start; // 밀리초 차이
    const diffInHours = diffInMs / (1000 * 60 * 60); // 시간으로 변환

    if (isNaN(diffInHours) || diffInHours < 0) return "N/A"; // 유효하지 않은 결과
    return `${diffInHours.toFixed(2)}`; // 소수점 2자리로 시간 반환
}

// 클릭 했을 때 휴가 시간 로컬 시간대로 변환(이거 안 하면 시간 표기가 안 됨) / 연장 근무 시작 시간도 포매팅
function formatToLocaleDate(isoDate) {
    if (!isoDate) return "N/A"; // null 값 처리

    const date = new Date(isoDate);
    return date.toLocaleString("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        hour12: false, // 24시간제 사용
    });
}


// 클릭 시 상세 조회 모달창 (연장근무)
document.getElementById("employee-table-body2").addEventListener("click" , (event) => {
    const row = event.target.closest("tr");
    if (row) {
        // 선택된 행에 .selected 클래스 추가
        document.querySelectorAll("tr").forEach(tr => tr.classList.remove("selected"));
        row.classList.add("selected"); // 현재 행에 selected 클래스 추가

        const leaveType = row.children[1].textContent.trim();

        if (leaveType === "연장근무") {
            // 연장근무 데이터 삽입
            document.getElementById("status").value = row.children[0].textContent;
            document.getElementById("leader").value = row.getAttribute("data-leader") || "N/A";
            document.getElementById("position").value = row.children[4].textContent;
            document.getElementById("applicationOverTime").value = row.children[5].textContent;
            document.getElementById("approvalOverTime").value = row.children[6].textContent;
            document.getElementById("overTime").value = `${row.getAttribute("data-total-over-time") || "N/A"} 시간`;
            document.getElementById("workTime").value = formatToLocaleDate(row.getAttribute("data-start-over-day") || "N/A");
                                                            //formatToLocaleDate 이 함수 써줘야, 2024. 12. 31. 19:00 이런식으로 출력

            // 연장근무 모달 표시
            const modalElement = new bootstrap.Modal(document.getElementById("myModal3"), {});
            modalElement.show();

        } else if (leaveType === "반차" || leaveType === "연차") {
            // 휴가 데이터 삽입
            document.getElementById("status2").value = row.children[0].textContent;
            document.getElementById("leader2").value = row.getAttribute("data-leader") || "N/A";
            document.getElementById("position2").value = row.children[4].textContent;
            document.getElementById("applicationVacTime").value = row.children[5].textContent;
            document.getElementById("approvalVacTime").value = row.children[6].textContent;
            // document.getElementById("vacStartTime").value = row.getAttribute("data-vac-start") || "N/A";
            // document.getElementById("vacEndTime").value = row.getAttribute("data-vac-end") || "N/A";

            // 시간은 따로 함수 써서 변환해 줘야 표시된다.
            const vacStartDate = row.getAttribute("data-vac-start");
            const vacEndDate = row.getAttribute("data-vac-end");

            // 변환된 날짜 표시
            document.getElementById("vacStartTime").value = formatToLocaleDate(vacStartDate);
            document.getElementById("vacEndTime").value = formatToLocaleDate(vacEndDate);

            // 휴가 모달 표시
            const modalElement = new bootstrap.Modal(document.getElementById("myModal4"), {});
            modalElement.show();
        }
    }
});

//////////////////////////////////
// 승인 및 반려 클릭 시 서버에 보내는 애 (update)
document.querySelector("#myModal4 .modal-footer").addEventListener("click", (e) => {
    if (e.target.textContent.trim() === "승인") {
        updateDraftState(2); // 승인 상태
    } else if (e.target.textContent.trim() === "반려") {
        updateDraftState(9); // 반려 상태
    }
});

// 상태 업데이트 함수
function updateDraftState(newState) {
    const draftNo = getSelectedDraftNo(); // 선택된 draft 번호 가져오기
    if (!draftNo) {
        alert("유효한 draft 번호가 없습니다.");
        return;
    }

    fetch("/employee/updateDraftState", { // 절대 경로 사용
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        },
        body: `draftNo=${draftNo}&newState=${newState}`, // 쿼리 스트링 형식으로 전송
    })
        .then((res) => res.json())
        .then((response) => {
            if (response.success) {
                alert("상태가 성공적으로 업데이트되었습니다.");
                // 테이블 재로드 또는 업데이트 로직 수행
                document.getElementById("employee-table-body2").innerHTML = "";
                loadEmployees(); // 새로고침 함수 호출
            } else {
                alert("상태 업데이트에 실패했습니다: " + (response.message || "알 수 없는 이유"));
            }
        })
        .catch((error) => console.error("상태 업데이트 중 오류 발생:", error));
}

// 승인이나 반려 눌렀을 때 데이터 새로고침. 근데 안 됨....................
function loadEmployees() {
    fetch("/employee/appStatusList") // 서버에서 데이터 요청
        .then(res => res.json())
        .then(data => {
            allEmployees = data; // 데이터를 갱신
            renderTable(allEmployees); // 테이블 다시 그리기
        })
        .catch(err => console.error("데이터 로드 실패:", err));
}


// 선택된 draftNo를 가져오는 함수
function getSelectedDraftNo() {
    const row = document.querySelector("tr.selected"); // 선택된 row 가져오기
    if (row) {
        const draftNo = row.getAttribute("data-draft-no");
        if (!draftNo) {
            alert("선택된 항목에 유효한 draft 번호가 없습니다.");
        }
        return draftNo;
    }
    alert("항목을 선택해주세요."); // 선택된 항목이 없는 경우 경고
    return null;
}


