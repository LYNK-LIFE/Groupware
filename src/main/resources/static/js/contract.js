//ps 사원 조회 자바스크립트

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

//=====================================================================================================================

//ps 고객 조회 버튼 클릭 시 모달 열기

document.getElementById('searchCustomerBtn').addEventListener('click', () => {
    const modal = new bootstrap.Modal(document.getElementById('customerModal'));
    modal.show();

    // 고객 데이터 요청
    fetch('/db/customers')
        .then(response => response.json())
        .then(data => renderCustomerList(data))
        .catch(error => console.error('Error fetching customers:', error));
});

// 검색 필터링
document.getElementById('customerSearch').addEventListener('input', function () {
    const searchValue = this.value.toLowerCase();
    const customers = document.querySelectorAll('#customerList li');

    customers.forEach(customer => {
        const text = customer.textContent.toLowerCase();
        customer.style.display = text.includes(searchValue) ? '' : 'none';
    });
});

// 고객 리스트 렌더링
function renderCustomerList(customerData) {
    const list = document.getElementById('customerList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (customerData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>';
        return;
    }

    customerData.forEach(customer => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${customer.customerName} (${customer.customerMobile})`;

        li.addEventListener('click', () => {
            document.getElementById('customerName').value = customer.customerName;
            document.getElementById('customerSsn').value = customer.customerSsn;
            document.getElementById('customerEmail').value = customer.customerEmail;
            document.getElementById('customerMobile').value = customer.customerMobile;

            const modal = bootstrap.Modal.getInstance(document.getElementById('customerModal'));
            modal.hide();
        });

        list.appendChild(li);
    });
}
//======================================================================================================================

document.addEventListener('DOMContentLoaded', function () {
    fetch('/db/insuranceCodes')
        .then(response => response.json())
        .then(data => populateInsuranceDropdown(data))
        .catch(error => console.error('Error fetching insurance codes:', error));
});

function populateInsuranceDropdown(codes) {
    const dropdown = document.getElementById('insuranceCode');
    dropdown.innerHTML = '<option value="">보험회사명을 선택하세요</option>'; // 기본 옵션

    codes.forEach(code => {
        const option = document.createElement('option');
        option.value = code.code;
        option.textContent = `${code.code} - ${code.name}`;
        dropdown.appendChild(option);
    });
}

//====================================================================================================================

document.getElementById('searchProductBtn').addEventListener('click', () => {
    const modal = new bootstrap.Modal(document.getElementById('productModal'));
    modal.show();

    fetch('/db/displayProduct')
        .then(response => response.json())
        .then(data => renderProductList(data))
        .catch(error => console.error('Error fetching products:', error));
});

function renderProductList(productData) {
    const list = document.getElementById('productList');
    list.innerHTML = ''; // 기존 데이터를 초기화

    if (productData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>';
        return;
    }

    productData.forEach(product => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${product.productName} (${product.productNo})`;

        li.addEventListener('click', () => {
            document.getElementById('productName').value = product.productName;

            const modal = bootstrap.Modal.getInstance(document.getElementById('productModal'));
            modal.hide();
        });

        list.appendChild(li);
    });
}

