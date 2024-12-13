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
    const keyword = keywordInput.value.trim(); // 입력 키워드

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
    if (filteredEmployees.length === 0) {
        alert("검색 결과가 없습니다."); // 메시지 알림
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