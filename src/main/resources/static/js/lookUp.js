
// 페이지 입장 시, 전체 데이터 출력

let allEmployees = []; // 데이터를 저장할 변수

// 페이지 로드 시 데이터를 가져와서 출력
document.addEventListener("DOMContentLoaded", () => {
    fetch("/employee/lookUpList")
        .then(res => res.json())
        .then(data => {
            allEmployees = data; // 데이터 저장
            renderTable(allEmployees); // 전체 데이터를 테이블에 출력
            console.log("전체 데이터 불러오기 성공:", allEmployees);
        })
        .catch(err => console.error("전체 데이터 불러오기 실패:", err));
});

// 테이블 데이터를 출력하는 함수
function renderTable(data) {
    const tableBody = document.getElementById("employee-table-body");
    tableBody.innerHTML = ""; // 테이블 초기화

    if (data.length === 0) { // 검색 결과가 없을 때
        const noDataRow = `<tr><td colspan="6">검색 결과가 없습니다.</td></tr>`;
        tableBody.innerHTML = noDataRow;
        return;
    }

    data.forEach(item => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.departmentDTO.depName}</td>
            <td>${item.humanDTO.position}</td>
            <td>${item.humanDTO.employeementStatus}</td>
            <td>${item.humanDTO.phoneNumber}</td>
        `;
        tableBody.appendChild(row);
    });
}

// 조회 기능 (필터링해서 출력)
function performSearch() {
    const type = document.getElementById("lookupSelect").value; // 부서/이름/고용구분 선택
    const keywordInput = document.getElementById("lookupInput");
    const keyword = keywordInput.value.trim(); // 입력 키워드 (trim으로 공백 제거)

    if (!keyword) {
        alert("검색어를 입력해 주세요.");
        return;
    }

    // 조건에 맞게 데이터 필터링
    const filteredEmployees = allEmployees.filter(employee => {
        if (type === "부서명") {
            return employee.departmentDTO.depName.includes(keyword);
        } else if (type === "이름") {
            return employee.name.includes(keyword);
        } else if (type === "고용 구분") {
            return employee.humanDTO.employeementStatus.includes(keyword);
        }
    });

    // 필터링된 데이터를 테이블에 출력
    renderTable(filteredEmployees);

    // 입력 필드 초기화
    keywordInput.value = "";
}

// 버튼 클릭 이벤트
document.getElementById("select-button-id").addEventListener("click", performSearch);

// Enter 키 이벤트 추가 (검색 입력창)
document.getElementById("lookupInput").addEventListener("keyup", (event) => {
    if (event.key === "Enter") {
        performSearch(); // 엔터 키 입력 시 조회 기능 실행
    }
});

// 클릭 이벤트: 테이블 행 클릭 시 모달 창 표시
document.getElementById("employee-table-body").addEventListener("click", (event) => {
    // 이벤트 위임을 사용해 클릭된 요소를 탐색
    const row = event.target.closest("tr"); // 클릭된 셀의 가장 가까운 "tr" 행
    if (row) {
        const cells = row.getElementsByTagName("td"); // 클릭된 행의 모든 <td> 가져오기

        const employeeData = {
            id: cells[0].textContent,
            name: cells[1].textContent,
            depNo: cells[2].textContent,
            position: cells[3].textContent,
            employeementStatus: cells[4].textContent,
            phoneNumber: cells[5].textContent
        };

        // 모달 폼에 데이터 설정
        document.getElementById("editId").value = employeeData.id;
        document.getElementById("editName").value = employeeData.name;
        document.getElementById("editDepNo").value = employeeData.depNo;
        document.getElementById("editPosition").value = employeeData.position;
        document.getElementById("editStatus").value = employeeData.employeementStatus;
        document.getElementById("editPhone").value = employeeData.phoneNumber;

        // Bootstrap 모달 표시
        const modalElement = new bootstrap.Modal(document.getElementById("myModal"), {});
        modalElement.show(); // 모달 열기
    }
});

// 클릭 시 수정 가능 모달
// 직원 테이블에서 행 클릭 이벤트
document.getElementById("saveChanges").addEventListener("click", () => {
    const updatedEmployee = {
        id: document.getElementById("editId").value,
        name: document.getElementById("editName").value,
        depNo: document.getElementById("editDepNo").value,
        humanDTO: { // HumanDTO 객체를 포함
            position: document.getElementById("editPosition").value,
            employeementStatus: document.getElementById("editStatus").value,
            phoneNumber: document.getElementById("editPhone").value,
        }
    };

    // 서버에 저장하는 로직
    console.log("전송할 데이터:", updatedEmployee);

    fetch("/employee/modify", {
        method: "POST", // HTTP 메서드
        headers: {
            "Content-Type": "application/json" // JSON 형식으로 데이터 전송
        },
        body: JSON.stringify(updatedEmployee) // 객체를 JSON 문자열로 변환
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
        .catch(err => console.error("직원 수정 실패:", err));

    // 모달 닫기
    const myModal = document.getElementById("myModal");
    myModal.style.display = "none";
});