
document.getElementById('searchEmployeeBtn').addEventListener('click', () => {
    const modal = new bootstrap.Modal(document.getElementById('employeeModal'));
    modal.show();
});
    document.getElementById('searchEmployeeBtn').addEventListener('click', function () {
    fetch('/db/employees')
        .then(response => response.json())
        .then(data => renderEmployeeList(data))
        .catch(error => console.error('Error fetching employees:', error));
    });

document.getElementById('employeeSearch').addEventListener('input', function () {
    const searchValue = this.value.toLowerCase();
    const employees = document.querySelectorAll('#employeeList li');

    employees.forEach(employee => {
        const text = employee.textContent.toLowerCase();
        employee.style.display = text.includes(searchValue) ? '' : 'none';
    });
});

function renderEmployeeList(employeeData) {
    const list = document.getElementById('employeeList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (employeeData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>';
        return;
    }

    employeeData.forEach(employee => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${employee.employeeName} (${employee.employeeNo})`;
        li.addEventListener('click', () => {
            document.getElementById('employeeName').value = employee.employeeName;
            const modal = bootstrap.Modal.getInstance(document.getElementById('employeeModal'));
            modal.hide();
        });
        list.appendChild(li);
    });
}
