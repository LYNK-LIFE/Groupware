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

// Calendar setup for month picker
document.getElementById('monthPicker').addEventListener('click', function () {
    const calendarEl = document.createElement('div');
    document.body.appendChild(calendarEl);

    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'ko',
        dateClick: function (info) {
            document.getElementById('monthPicker').value = info.dateStr;
            document.body.removeChild(calendarEl);
        }
    });
    calendar.render();
});

// Function to update table
function updateTable(data) {
    const tableBody = document.getElementById('resultTable');
    tableBody.innerHTML = '';

    if(!Array.isArray(data)) {
        console.error('Returned data is not an array:', data);
        tableBody.innerHTML = '<tr><td colspan="6">조회 결과가 없습니다.</td></tr>';
        return;
    }
    data.forEach(item => {
        const row = `
            <tr>
                <td>${item.contractNo}</td>
                <td>${item.insuranceCompany}</td>
                <td>${item.productName}</td>
                <td>${item.contractName} / ${item.insuredName}</td>
                <td>${item.departmentName} / ${item.employeeName} / ${item.employeeNo}</td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
