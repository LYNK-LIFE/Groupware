// 파일 첨부 - 파일 이름 표시
document.addEventListener("DOMContentLoaded", function () {
    const addFileButton = document.getElementById("addFileButton");
    const fileInput = document.getElementById("fileInput");
    const fileName = document.getElementById("fileName");

    // 파일 선택 버튼 클릭
    addFileButton.addEventListener("click", function () {
        fileInput.click();
    });

    // 파일이 선택되면 파일 이름 표시
    fileInput.addEventListener("change", function () {
        const file = fileInput.files[0];
        fileName.textContent = file ? `파일 이름: ${file.name}`: "파일이 선택되지 않았습니다.";
        fileName.style.display = "block";
    });
});

// 일반품의 표 작성 관련 자바스크립트
document.getElementById("draftButton").addEventListener("click", function () {
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

    // 데이터 유효성 검사
    for (const key in formData) {
        if (!formData[key]) {
            alert(`${key} 필드를 입력해주세요.`);
            return;
        }
    }
    // 기안하기 동작 수행
    console.log("기안 내용:", formData);
    alert("기안이 완료되었습니다!");

    // 서버로 데이터 전송 (백엔드 연동 시 사용)
    fetch('/submit-draft', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => response.json())
        .then(data => {
            alert('기안이 성공적으로 저장되었습니다.');
        })
        .catch(error => {
            console.error('Error:', error);
        });

});


// 결재선

document.addEventListener("DOMContentLoaded", function () {
    const approverTable = document.getElementById("approverTable");
    const approvalModal = document.getElementById("approvalModal");
    const selectedApproverList = document.getElementById("selectedApproverList"); // 선택된 결재자 리스트
    const selectedApprovers = new Map(); // 중복 추가 방지를 위한 Map

    // 결재자 데이터 불러오기
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

    // 모달 열릴 때 결재자 데이터 불러오기
    approvalModal.addEventListener("shown.bs.modal", loadApproverData);

    // 결재자 선택 이벤트 처리
    approverTable.addEventListener("click", function (event) {
        if (event.target.classList.contains("selectApprover")) {
            const employee = JSON.parse(event.target.dataset.employee);

            // 중복 추가 방지
            if (selectedApprovers.has(employee.employeeNo)) {
                alert("이미 선택된 결재자입니다.");
                return;
            }

            // 선택된 결재자 추가
            addApprover(employee);
        }
    });

    // 선택된 결재자 추가 함수
    function addApprover(employee) {
        // Map에 추가
        selectedApprovers.set(employee.employeeNo, { ...employee , role:"결재"});


        // 리스트에 추가
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
                    <button class="btn btn-danger btn-sm removeApprover">삭제</button> `;

        selectedApproverList.appendChild(li);
    }

    // 선택된 결재자 삭제 이벤트 처리
    selectedApproverList.addEventListener("click", function (event) {
        if (event.target.classList.contains("removeApprover")) {
            const li = event.target.parentElement;
            const employeeNo = li.dataset.employeeNo;

            // Map에서 제거
            selectedApprovers.delete(employeeNo);

            // 리스트에서 제거
            li.remove();
        }
    });

    function saveApproversToServer() {
        const approverArray = Array.from(selectedApprovers.values());
        fetch("/save-approvers", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(approverArray)
        }).then(response => {
            if (response.ok) {
                alert("결재자가 성공적으로 저장되었습니다.");
            } else {
                alert("저장 중 문제가 발생했습니다.");
            }
        });
    }
});

// 첨부파일 화면 올리고 뒤로 사라지기
document.addEventListener("DOMContentLoaded", function () {
    const fileInput = document.getElementById("fileInput");
    const uploadButton = document.getElementById("uploadButton");
    const previewImage = document.getElementById("previewImage");
    const deleteFileButton = document.getElementById("deleteFileButton");

    // 파일 첨부 버튼 클릭 시 input 트리거 (중복 방지)
    uploadButton.addEventListener("click", (e) => {
        e.preventDefault(); // 기본 동작 방지
        e.stopPropagation(); // 이벤트 전파 방지
        fileInput.value = ""; // 기존 선택된 파일 초기화
        fileInput.click();
    });

    // 파일 선택 시 이미지 미리보기 표시
    fileInput.addEventListener("change", () => {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewImage.src = e.target.result; // 이미지 경로 설정
                previewImage.style.display = "block"; // 이미지 표시
                uploadButton.style.display = "none"; // 파일 첨부 버튼 숨김
                deleteFileButton.style.display = "block"; // 삭제 버튼 표시
            };
            reader.readAsDataURL(file);
        }
    });

    // 삭제 버튼 클릭 시 파일 초기화
    deleteFileButton.addEventListener("click", () => {
        fileInput.value = ""; // 파일 input 초기화
        previewImage.src = ""; // 이미지 src 초기화
        previewImage.style.display = "none"; // 이미지 숨김
        uploadButton.style.display = "block"; // 파일 첨부 버튼 표시
        deleteFileButton.style.display = "none"; // 삭제 버튼 숨김
    });
});

// 기안하기 버튼을 눌르면 이제 한번에 저장하기 위한 gpt의 빌드업

document.getElementById("draftButton").addEventListener("click", function () {
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
        const employeeNo = item.dataset.employeeNo; // 결재자의 고유 번호
        const role = item.querySelector('.approver-role').value; // 선택된 역할 (결재/기안/참조)

        approverArray.push({
            employeeNo: employeeNo,
            role: role
        });
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
    const payload = {
        ...formData,
        approvers: approverArray
    };

    // 서버로 전송
    fetch('/submit-draft', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
    })
        .then(response => response.json())
        .then(data => {
            alert('기안이 성공적으로 저장되었습니다.');
        })
        .catch(error => {
            console.error('Error:', error);
            alert("저장 중 문제가 발생했습니다.");
        });
});
