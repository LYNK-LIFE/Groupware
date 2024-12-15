// 데이터를 저장할 변수
let allEmployees = [];

// Fetch로 데이터 가져오기
fetch("/employee/lookUpList")
    .then(res => res.json())
    .then(data => {
        allEmployees = data; // 데이터 저장
        console.log(allEmployees); // 데이타 잘 들어있나 확인!
    })
    .catch(err => console.error("Error fetching lookup list:", err));

// 조회 기능
function performSearch() {
    const type = document.getElementById("lookupSelect").value; // 사번 또는 이름 선택
    const keywordInput = document.getElementById("lookupInput");
    const keyword = keywordInput.value.trim(); // 입력 키워드 (trim은 양쪽 공백 제거)

    if (!keyword) {
        alert("검색어를 입력해 주세요.");
        return;
    }

    // 필터링된 데이터를 가져옴
    const filteredEmployees = allEmployees.filter(employee => {
        if (type === "사번") {
            return String(employee.id).includes(keyword); // 사번 검색
        } else if (type === "이름") {
            return employee.name.includes(keyword); // 이름 검색
        }
    });

    const tableBody = document.getElementById("employee-table-body");

    // 검색 결과가 없는 경우 메시지 추가
    if (filteredEmployees.length === 0) { // === 은 타입과 값 모두 같아야함 , == 는 값만 같으면됨
                                        // ex) 5 === '5' false , 5 == '5' true
        alert("검색 결과가 없습니다.");
    } else {
        // 필터링된 데이터를 테이블에 추가
        filteredEmployees.forEach(item => {
            const row = document.createElement("tr");

            row.innerHTML = `
                            <td>${item.id}</td>
                            <td>${item.name}</td>
                            <td>${item.humanDTO.position}</td>
                            <td>${item.humanDTO.employeementStatus}</td>
                            <td>${item.humanDTO.phoneNumber}</td>
                            <td>${item.humanDTO.ssn}</td>
                        `;

            tableBody.appendChild(row); // 새 행을 테이블 끝에 추가
        });
    }

    // 입력 필드 초기화
    keywordInput.value = "";
}

// 버튼 클릭 이벤트
document.getElementById("select-button-id").addEventListener("click", performSearch);

// Enter 키 이벤트 추가
document.getElementById("lookupInput").addEventListener("keyup", (event) => {
    if (event.key === "Enter") {
        performSearch(); // 엔터 키로 검색 실행
    }
});

///////////////////////
// 클릭 시 수정 가능 모달창 띄우는 구문!
// 테이블 행 클릭 이벤트에서 모달 창 띄우기
document.getElementById("employee-table-body").addEventListener("click", (event) => {
    // event 쓸 때와 안 쓸때의 차이 : 이벤트 발생 시 세부내역 참조(이벤트 정보), 안 쓸때엔 세부정보 알 수 없음~~
    // closest : 가장 가까운 조상 요소 찾음!!!
    const row = event.target.closest("tr"); // 클릭한 행 찾기
    if (row) {
        const cells = row.getElementsByTagName("td"); // 문자열로 "td","tr"감싸야 실제로 찾을 수 있고
                                                    // 감싸지 않으면 변수를 찾는다는 뜻, 에러난다~~
        const employeeData = {
            id: cells[0].textContent,
            name: cells[1].textContent,
            position: cells[2].textContent,
            employeementStatus: cells[3].textContent,
            phoneNumber: cells[4].textContent,
            ssn: cells[5].textContent
        };

        // 모달 폼 데이터 설정
        document.getElementById("editId").value = employeeData.id;
        document.getElementById("editName").value = employeeData.name;
        document.getElementById("editPosition").value = employeeData.position;
        document.getElementById("editStatus").value = employeeData.employeementStatus;
        document.getElementById("editPhone").value = employeeData.phoneNumber;
        document.getElementById("editSsn").value = employeeData.ssn;

        // 모달 창 표시 (Bootstrap 방식 사용)
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
        humanDTO: { // HumanDTO 객체를 포함
            position: document.getElementById("editPosition").value,
            employeementStatus: document.getElementById("editStatus").value,
            phoneNumber: document.getElementById("editPhone").value,
            ssn: document.getElementById("editSsn").value
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
