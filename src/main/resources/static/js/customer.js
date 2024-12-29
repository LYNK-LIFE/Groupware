async function submitForm() {
    const customerData = {
        customerName: document.getElementById('customerName').value,
        customerSsn: document.getElementById('customerSsn').value,
        customerMobile: document.getElementById('customerMobile').value,
        customerAddr: document.getElementById('customerAddr').value,
        customerEmail: document.getElementById('customerEmail').value
    };

    console.log(customerData);

    await fetch('/db/customer', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(customerData)
    });
    fetchCustomerList();
}
// ================================================================================

async function fetchCustomerList() {
    const response = await fetch('/db/customer/list');
    const customers = await response.json();

    const tableBody = document.querySelector("#customerTable tbody");
    tableBody.innerHTML = "";
    customers.forEach(customer => {
        const row = `
            <tr>
                <td>${customer.customerNo}</td>
                <td>${customer.customerName}</td>
                <td>${customer.customerSsn}</td>
                <td>${customer.customerMobile}</td>
                <td>${customer.customerAddr}</td>
                <td><button onclick="deleteCustomer(${customer.customerNo})">삭제</button></td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}
async function deleteCustomer(customerNo) {
    await fetch(`/db/customer/delete/${customerNo}`, { method: 'DELETE' });
    fetchCustomerList();
}

// Initial load
fetchCustomerList();