document.querySelectorAll('.btn-primary').forEach(button => {
    button.addEventListener('click', async (event) => {
        const params = new URLSearchParams();

        // Determine which button triggered the event
        const buttonId = event.target.id;

        // Add parameters based on the button clicked
        if (buttonId === 'nameSearchButton') {
            const customerName = document.getElementById('nameSearch').value;
            if (customerName) params.append('customerName', customerName);
        } else if (buttonId === 'ssnSearchButton') {
            const ssn = document.getElementById('ssnSearch').value;
            if (ssn) params.append('customerSsn', ssn);
        } else if (buttonId === 'employeeSearchButton') {
            const employeeSearch = document.getElementById('employeeSearch').value;
            if (employeeSearch) params.append('employeeNo', employeeSearch);
        } else if (buttonId === 'monthSearchButton') {
            const month = document.getElementById('monthPicker').value;
            if (month) params.append('month', month);
        }

        // Fetch data
        const response = await fetch(`/db/expiringcustomer/search?${params.toString()}`);
        const results = await response.json();

        // Update table
        updateTable(results);
    });
});

// 월별 조회를 위한 달력 설정
// 달력 클릭 시 월 선택 가능
// 선택된 월에 해당하는 데이터를 가져와 테이블 업데이트

document.getElementById('monthPicker').addEventListener('click', function () {
    const calendarEl = document.createElement('div');
    calendarEl.style.position = 'absolute';
    calendarEl.style.zIndex = 9999;
    calendarEl.style.backgroundColor = '#fff';
    calendarEl.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.2)';
    calendarEl.style.padding = '20px';

    // 클릭한 버튼의 위치에 맞춰 달력 위치 설정
    const rect = event.target.getBoundingClientRect();
    calendarEl.style.left = `${rect.left}px`;
    calendarEl.style.top = `${rect.bottom + window.scrollY}px`;

    document.body.appendChild(calendarEl);

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: ''
        },
        dateClick: function (info) {
            const selectedDate = new Date(info.dateStr);
            const selectedMonth = selectedDate.getMonth() + 1; // 월 (0부터 시작하므로 +1)
            const selectedYear = selectedDate.getFullYear(); // 연도

            // 선택된 월에 대한 만기 고객 목록 가져오기
            fetchExpiringCustomersByMonth(selectedYear, selectedMonth);

            // 달력 제거
            document.body.removeChild(calendarEl);
        }
    });

    calendar.render();
});

// 월별 데이터를 서버에서 가져오기
async function fetchExpiringCustomersByMonth(year, month) {
    try {
        const response = await fetch(`/db/expiringcustomer/month?year=${year}&month=${month}`);
        if (!response.ok) throw new Error('데이터를 가져오지 못했습니다.');

        const results = await response.json();
        updateTable(results);
    } catch (error) {
        console.error('월별 조회 중 오류 발생:', error);
    }
}

// 조회 버튼 클릭 이벤트 핸들러
// 계약자, 주민등록번호, 사번 등으로 조회

document.querySelectorAll('.btn-primary').forEach(button => {
    button.addEventListener('click', async (event) => {
        const params = new URLSearchParams();

        // 어떤 버튼이 클릭되었는지 확인
        const buttonId = event.target.id;

        // 클릭된 버튼에 따라 파라미터 추가
        if (buttonId === 'nameSearchButton') {
            const customerName = document.getElementById('nameSearch').value;
            if (customerName) params.append('customerName', customerName);
        } else if (buttonId === 'ssnSearchButton') {
            const ssn = document.getElementById('ssnSearch').value;
            if (ssn) params.append('customerSsn', ssn);
        } else if (buttonId === 'employeeSearchButton') {
            const employeeSearch = document.getElementById('employeeSearch').value;
            if (employeeSearch) params.append('employeeNo', employeeSearch);
        } else if (buttonId === 'monthSearchButton') {
            const month = document.getElementById('monthPicker').value;
            if (month) params.append('month', month);
        }

        try {
            // 데이터 요청
            const response = await fetch(`/db/expiringcustomer/search?${params.toString()}`);
            if (!response.ok) throw new Error('데이터 조회에 실패했습니다.');

            const results = await response.json();
            updateTable(results);
        } catch (error) {
            console.error('조회 중 오류 발생:', error);
        }
    });
});

// 테이블 업데이트 함수
// 서버에서 가져온 데이터를 테이블에 반영
function updateTable(data) {
    const tableBody = document.getElementById('resultTable');
    tableBody.innerHTML = '';

    if (!Array.isArray(data)) {
        console.error('반환된 데이터가 배열이 아닙니다:', data);
        tableBody.innerHTML = '<tr><td colspan="7">조회 결과가 없습니다.</td></tr>';
        return;
    }

    data.forEach(item => {
        // 계약일자로 만기일자 계산
        const contractDate = new Date(item.contractDate);
        const expiringDate = new Date(contractDate);
        expiringDate.setDate(contractDate.getDate() + 365); // +365일

        const formattedExpiringDate = expiringDate.toISOString().split('T')[0]; // yyyy-MM-dd 형식으로 변환
        const row = `
            <tr>
                <td>${item.contractNo}</td>
                <td>${item.insuranceCompany}</td>
                <td>${item.productName}</td>
                <td>${formattedExpiringDate}</td>
                <td>${item.contractName} / ${item.insuredName}</td>
                <td>${item.departmentName} / ${item.employeeName} / ${item.employeeNo}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
