document.addEventListener("DOMContentLoaded", function () {
    // **파일 첨부 관련 이벤트 설정**
    const addFileButton = document.getElementById("addFileButton");
    const fileInput = document.getElementById("fileInput");
    const fileName = document.getElementById("fileName");

    // 파일 선택 버튼 클릭 시, 파일 입력 필드를 클릭하도록 트리거
    addFileButton.addEventListener("click", function () {
        fileInput.click();
    });

    // 파일이 선택되면 파일 이름을 화면에 표시
    fileInput.addEventListener("change", function () {
        const file = fileInput.files[0];
        fileName.textContent = file ? `파일 이름: ${file.name}` : "파일이 선택되지 않았습니다.";
        fileName.style.display = "block";
    });

    // **결재선 설정 관련 이벤트**
    const approverTable = document.getElementById("approverTable");
    const approvalModal = document.getElementById("approvalModal");
    const selectedApproverList = document.getElementById("selectedApproverList");
    const selectedApprovers = new Map();

    // 결재자 데이터 불러오기 함수 (서버에서 결재자 목록 가져옴)
    function loadApproverData() {
        fetch("/payment/approvers")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Failed to fetch data");
                }
                return response.json();
            })
            .then(data => {
                approverTable.innerHTML = ""; // 기존 데이터 초기화
                data.forEach(employee => {
                    const row = document.createElement("tr");
                    // 결재자 목록을 동적으로 테이블에 추가
                    row.innerHTML = `
                        <td>${employee.departmentDTO.departmentName}</td>
                        <td>${employee.employeeName}</td>
                        <td>${employee.humanResourceDTO.position}</td>
                        <td>
                            <button class="btn btn-primary btn-sm selectApprover"
                                    data-employee='${JSON.stringify(employee)}'>
                                선택
                            </button>
                        </td>
                    `;
                    approverTable.appendChild(row);
                });
            })
            .catch(error => {
                console.error("Error loading approver data:", error);
            });
    }

    // 모달 창 열릴 때 결재자 데이터를 로드
    approvalModal.addEventListener("shown.bs.modal", loadApproverData);

    // 결재자 선택 버튼 클릭 시 처리
    approverTable.addEventListener("click", function (event) {
        if (event.target.classList.contains("selectApprover")) {
            const employee = JSON.parse(event.target.dataset.employee);

            // 중복된 결재자 선택 방지
            if (selectedApprovers.has(employee.employeeNo)) {
                alert("이미 선택된 결재자입니다.");
                return;
            }

            // 선택된 결재자 추가
            addApprover(employee);
        }
    });

    // 선택된 결재자를 리스트에 추가하는 함수
    function addApprover(employee) {
        // Map에 결재자 정보를 저장
        selectedApprovers.set(employee.employeeNo, { ...employee, role: "결재" });

        // 리스트에 결재자 추가
        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between align-items-center";
        li.dataset.employeeNo = employee.employeeNo;

        li.innerHTML = `
            <div>
                ${employee.employeeName} (${employee.humanResourceDTO.position})
                <select class="form-select form-select-sm approver-role"
                    data-employee-no="${employee.employeeNo}"
                    style="width: 120px; display: inline-block; margin-left: 10px;">
                    <option value="기안">기안</option>
                    <option value="참조">참조</option>
                    <option value="결재" selected>결재</option>
                </select>
            </div>
            <button class="btn btn-danger btn-sm removeApprover">삭제</button>
        `;
        selectedApproverList.appendChild(li);
    }

    // 선택된 결재자 삭제 버튼 처리
    selectedApproverList.addEventListener("click", function (event) {
        if (event.target.classList.contains("removeApprover")) {
            const li = event.target.parentElement;
            const employeeNo = li.dataset.employeeNo;

            // Map에서 결재자 삭제
            selectedApprovers.delete(employeeNo);

            // 리스트에서 삭제
            li.remove();
        }
    });

    // **기안하기 버튼 이벤트**
    const draftButton = document.getElementById("draftButton");

    draftButton.addEventListener("click", function () {
        // 일반 품의서 폼 데이터 수집
        const formData = {
            documentNumber: document.getElementById('documentNumber').value,
            documentDate: document.getElementById('documentDate').value,
            department: document.getElementById('department').value,
            amount: document.getElementById('amount').value,
            author: document.getElementById('author').value,
            preservation: document.getElementById('preservation').value,
            title: document.getElementById('title').value,
            content: document.getElementById('content').value,
        };

        // 결재선 데이터 수집
        const approverArray = [];
        document.querySelectorAll("#selectedApproverList .list-group-item").forEach(item => {
            const employeeNo = item.dataset.employeeNo;
            const role = item.querySelector('.approver-role').value;
            approverArray.push({ employeeNo, role });
        });

        // 유효성 검사
        for (const key in formData) {
            if (!formData[key]) {
                alert(`${key} 필드를 입력해주세요.`);
                return;
            }
        }

        if (approverArray.length === 0) {
            alert("결재자를 선택해주세요.");
            return;
        }

        // 최종 데이터 병합
        const payload = { ...formData, approvers: approverArray };

        // 서버로 데이터 전송
        fetch('/payment/save', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload),
        })
            .then(response => {
                if (response.ok) return response.json();
                throw new Error("Network response was not ok");
            })
            .then(data => {
                alert('기안이 성공적으로 저장되었습니다.');
                console.log("서버 응답:", data);
            })
            .catch(error => {
                console.error("Error:", error);
                alert("저장 중 문제가 발생했습니다.");
            });
    });
});


// 부서 목록 조회 및 추가
document.addEventListener("DOMContentLoaded", function () {
    const departmentSelect = document.getElementById("department");

    // 서버에서 부서 목록 가져오기
    function loadDepartments() {
        fetch("/department")
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // 기존 옵션 초기화
                departmentSelect.innerHTML = "";
                data.forEach(department => {
                    const option = document.createElement("option");
                    option.value = department.department_no; // 실제 값
                    option.textContent = department.department_name; // 표시될 이름
                    departmentSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Error loading departments:", error);
                alert("부서 목록을 불러오는 데 실패했습니다.");
            });
    }

    // 초기화
    loadDepartments();
});




//작성자 목록 조회 및 선택
document.addEventListener("DOMContentLoaded", function () {
    const authorTable = document.getElementById("authorTable");
    const authorIdInput = document.getElementById("authorId");
    const authorNameInput = document.getElementById("authorName");

    // 서버에서 작성자 목록 가져오기
    function loadAuthors() {
        fetch("/authors")
            .then(response => response.json())
            .then(data => {
                authorTable.innerHTML = ""; // 기존 목록 초기화
                data.forEach(author => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${author.employeeId}</td>
                        <td>${author.employeeName}</td>
                        <td>${author.departmentName}</td>
                        <td>
                            <button class="btn btn-primary btn-sm selectAuthorButton"
                                    data-employee-id="${author.employeeId}"
                                    data-employee-name="${author.employeeName}">
                                선택
                            </button>
                        </td>
                    `;
                    authorTable.appendChild(row);
                });
            })
            .catch(error => console.error("Error loading authors:", error));
    }

    // 작성자 선택 버튼 이벤트 처리
    authorTable.addEventListener("click", function (event) {
        if (event.target.classList.contains("selectAuthorButton")) {
            const employeeId = event.target.dataset.employeeId;
            const employeeName = event.target.dataset.employeeName;

            // 선택된 값을 입력 필드에 설정
            authorIdInput.value = employeeId;
            authorNameInput.value = employeeName;

            // 모달 닫기
            const modal = bootstrap.Modal.getInstance(document.getElementById("authorModal"));
            modal.hide();
        }
    });

    // 모달이 열릴 때 작성자 목록 로드
    document.getElementById("authorModal").addEventListener("shown.bs.modal", loadAuthors);
});