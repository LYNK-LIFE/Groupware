document.addEventListener("DOMContentLoaded", () => {
    let allEmployees = [];
    const lookupSelect = document.getElementById("lookupSelect2");
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

            // 상태별 CSS 클래스 추가
            const statusClass = getStatusClass(item.approver);
            if (statusClass) {
                row.classList.add(statusClass); // CSS 클래스 추가
            }

            // 구분값 설정
            const category = getCategory(item);

            row.innerHTML = `
                <td>${getStatusLabel(item.approver)}</td>
                <td>${category}</td>
                <td>${item.departmentDTO?.depName || 'N/A'}</td>
                <td>${item.employeeDTO?.name || 'N/A'}</td>
                <td>${item.humanDTO?.position || 'N/A'}</td>
                <td>${formatDate(item.draftTime)}</td>
                <td>${formatDate(item.approveTime)}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    // 검색 버튼 클릭 시 동작
    document.getElementById("select-button-id").addEventListener("click", () => {
        const selectedStatus = lookupSelect.value; // 선택된 검색 상태
        const filteredData = allEmployees.filter(item => {
            return getStatusLabel(item.approver) === selectedStatus;
        });

        renderTable(filteredData);
    });

    // 상태 라벨 반환
    function getStatusLabel(status) {
        if (status === 0) return "대기";
        if (status === 1) return "승인";
        if (status === 2) return "반려";
        return "알 수 없음";
    }

    // 상태별 CSS 클래스 반환
    function getStatusClass(status) {
        if (status === 0) return "status-waiting";
        if (status === 1) return "status-approved";
        if (status === 2) return "status-rejected";
        return null;
    }

    // 구분값 반환 함수
    function getCategory(item) { // 연장 근무만 떴었는데 if 줄 바꾸니까 잘 적용
        // 휴가 조건: leaveDate 존재
        if (item.dayOffDTO?.leaveDate) {
            if (item.dayOffDTO.leaveType === 2) return "연차";
            if (item.dayOffDTO.leaveType === 1) return "반차";
        }
        // 연장 근무 조건: workOff > 18:00
        if (item.commuteDTO?.workOff && item.commuteDTO.workOff.slice(0, 5) > "18:00") {
            return "연장근무";
        }
        return "기타";
    }

    // 날짜 포맷 함수
    function formatDate(datetime) {
        if (!datetime) return "N/A"; // 값이 없을 경우 표시하지 않음
        const date = new Date(datetime);
        const yyyy = date.getFullYear();
        const mm = String(date.getMonth() + 1).padStart(2, "0");
        const dd = String(date.getDate()).padStart(2, "0");
        const hh = String(date.getHours()).padStart(2, "0");
        const mi = String(date.getMinutes()).padStart(2, "0");
        return `${yyyy}-${mm}-${dd} ${hh}:${mi}`;
    }
});



// document.addEventListener("DOMContentLoaded", () => {
//     let allEmployees = [];
//     const lookupSelect = document.getElementById("lookupSelect2");
//     const lookupInput = document.getElementById("lookupInput2");
//     const tableBody = document.getElementById("employee-table-body2");
//
//     // 페이지 로드 시 데이터 요청
//     fetch("/employee/appStatusList")
//         .then(res => res.json())
//         .then(data => {
//             allEmployees = Array.from(
//                 new Map(data.map(item => [JSON.stringify(item), item])).values()
//             ); // JSON.stringify를 기준으로 중복 제거
//             renderTable(allEmployees);
//             console.log("데이터 로드 성공:", allEmployees);
//         })
//         .catch(err => console.error("데이터 로드 실패:", err));
//
//     // 테이블 데이터 렌더링 함수
//     function renderTable(data) {
//         tableBody.innerHTML = "";
//         if (data.length === 0) {
//             tableBody.innerHTML = `<tr><td colspan="7">검색 결과가 없습니다.</td></tr>`;
//             return;
//         }
//
//         data.forEach(item => {
//             const row = document.createElement("tr");
//
//             // 상태별 배경색 추가
//             const statusClass = getStatusClass(item.approver);
//             row.className = statusClass;
//
//             // 구분값 설정
//             const category = getCategory(item);
//
//             row.innerHTML = `
//                 <td>${getStatusLabel(item.approver)}</td>
//                 <td>${category}</td>
//                 <td>${item.departmentDTO?.depName || 'N/A'}</td>
//                 <td>${item.employeeDTO?.name || 'N/A'}</td>
//                 <td>${item.humanDTO?.position || 'N/A'}</td>
//                 <td>${item.draftTime ? formatDate(item.draftTime) : 'N/A'}</td>
//                 <td>${item.approveTime ? formatDate(item.approveTime) : 'N/A'}</td>
//             `;
//             tableBody.appendChild(row);
//         });
//     }
//
//     // 상태 라벨 반환
//     function getStatusLabel(status) {
//         if (status === 0) return "대기";
//         if (status === 1) return "승인";
//         if (status === 2) return "반려";
//         return "알 수 없음";
//     }
//
//     // 상태별 CSS 클래스 반환
//     function getStatusClass(status) {
//         if (status === 0) return "status-waiting";
//         if (status === 1) return "status-approved";
//         if (status === 2) return "status-rejected";
//         return "";
//     }
//
//     // 구분값 반환 함수
//     function getCategory(item) {
//         // 연장 근무 조건: workOff > 18:00
//         if (item.commuteDTO?.workOff && item.commuteDTO.workOff.slice(0, 5) > "18:00") {
//             return "연장근무";
//         }
//         // 휴가 조건: leaveDate 존재
//         if (item.dayOffDTO?.leaveDate) {
//             if (item.dayOffDTO.leaveType === 2) return "연차";
//             if (item.dayOffDTO.leaveType === 1) return "반차";
//         }
//         return "기타";
//     }
//
//     // 날짜 포맷 함수
//     function formatDate(datetime) {
//         const date = new Date(datetime);
//         const yyyy = date.getFullYear();
//         const mm = String(date.getMonth() + 1).padStart(2, "0");
//         const dd = String(date.getDate()).padStart(2, "0");
//         const hh = String(date.getHours()).padStart(2, "0");
//         const mi = String(date.getMinutes()).padStart(2, "0");
//         return `${yyyy}-${mm}-${dd} ${hh}:${mi}`;
//     }
// });
