
// 설계사 조회 버튼 스크립트
document.getElementById('searchEmployeeBtn').addEventListener('click', () => {
    document.getElementById('employeeModal').style.display = 'block';
});

document.getElementById('employeeSearch').addEventListener('input', function () {
    const employeeName = this.value;

    fetch(`/db/contract/search?employeeName=${employeeName}`)
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('employeeList');
            list.innerHTML = ''; // 기존 목록 초기화


            data.forEach(employee => {
                const li = document.createElement('li');
                li.textContent = `${employee.employeeName} (${employee.employeeNo})`;
                li.classList.add('employee-item');

                li.addEventListener('click', () => {
                    document.getElementById('employeeName').value = employee.employeeName;
                    document.getElementById('employeeModal').style.display = 'none';
                });
                list.appendChild(li);
            });
        });
});

//=================================================================================================================


