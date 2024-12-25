//ps 사원 조회 자바스크립트

// 사원 검색 버튼 클릭 이벤트 핸들러
// 사원 모달 열기 및 서버에서 데이터 요청
document.getElementById('searchEmployeeBtn').addEventListener('click', () => {
    // 모달 열기
    const modal = new bootstrap.Modal(document.getElementById('employeeModal'));
    modal.show();

    // 서버에서 사원 데이터 요청
    fetch('/db/employees')
        .then(response => response.json()) // 응답 데이터를 JSON으로 파싱
        .then(data => renderEmployeeList(data)) // 데이터 렌더링 함수 호출
        .catch(error => console.error('사원 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 검색 입력 필드에 따라 사원 목록 필터링
document.getElementById('employeeSearch').addEventListener('input', function () {
    const searchValue = this.value.toLowerCase(); // 입력값 소문자로 변환
    const employees = document.querySelectorAll('#employeeList li'); // 사원 목록 항목들

    // 검색 조건에 맞는 항목만 표시
    employees.forEach(employee => {
        const text = employee.textContent.toLowerCase();
        employee.style.display = text.includes(searchValue) ? '' : 'none';
    });
});

// 사원 데이터를 렌더링하는 함수
function renderEmployeeList(employeeData) {
    const list = document.getElementById('employeeList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (employeeData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>'; // 검색 결과 없을 시 메시지 표시
        return;
    }

    // 사원 데이터를 기반으로 목록 생성
    employeeData.forEach(employee => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${employee.employeeName} (${employee.employeeNo})`;
        li.addEventListener('click', () => {
            document.getElementById('employeeName').value = employee.employeeName; // 선택한 사원 이름 설정
            const modal = bootstrap.Modal.getInstance(document.getElementById('employeeModal'));
            modal.hide(); // 모달 닫기
        });
        list.appendChild(li);
    });
}

//=====================================================================================================================

//ps 고객 조회 버튼 클릭 시 모달 열기

// 고객 검색 버튼 클릭 이벤트 핸들러
document.getElementById('searchCustomerBtn').addEventListener('click', () => {
    const modal = new bootstrap.Modal(document.getElementById('customerModal'));
    modal.show();

    // 서버에서 고객 데이터 요청
    fetch('/db/customers')
        .then(response => response.json()) // 응답 데이터를 JSON으로 파싱
        .then(data => renderCustomerList(data)) // 데이터 렌더링 함수 호출
        .catch(error => console.error('고객 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 검색 입력 필드에 따라 고객 목록 필터링
document.getElementById('customerSearch').addEventListener('input', function () {
    const searchValue = this.value.toLowerCase(); // 입력값 소문자로 변환
    const customers = document.querySelectorAll('#customerList li'); // 고객 목록 항목들

    // 검색 조건에 맞는 항목만 표시
    customers.forEach(customer => {
        const text = customer.textContent.toLowerCase();
        customer.style.display = text.includes(searchValue) ? '' : 'none';
    });
});

// 고객 데이터를 렌더링하는 함수
function renderCustomerList(customerData) {
    const list = document.getElementById('customerList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (customerData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>'; // 검색 결과 없을 시 메시지 표시
        return;
    }

    // 고객 데이터를 기반으로 목록 생성
    customerData.forEach(customer => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${customer.customerName} (${customer.customerMobile})`;

        li.addEventListener('click', () => {
            document.getElementById('customerName').value = customer.customerName; // 선택한 고객 이름 설정
            document.getElementById('customerSsn').value = customer.customerSsn; // 선택한 고객 주민번호 설정
            document.getElementById('customerEmail').value = customer.customerEmail; // 선택한 고객 이메일 설정
            document.getElementById('customerMobile').value = customer.customerMobile; // 선택한 고객 전화번호 설정

            const modal = bootstrap.Modal.getInstance(document.getElementById('customerModal'));
            modal.hide(); // 모달 닫기
        });

        list.appendChild(li);
    });
}

//======================================================================================================================

// 보험 회사 드롭다운 데이터를 채우는 함수
document.addEventListener('DOMContentLoaded', function () {
    fetch('/db/insuranceCodes')
        .then(response => response.json())
        .then(data => populateInsuranceDropdown(data)) // 데이터 렌더링 함수 호출
        .catch(error => console.error('보험 회사 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 보험 회사 드롭다운 데이터 렌더링 함수
function populateInsuranceDropdown(codes) {
    const dropdown = document.getElementById('insuranceCode');
    dropdown.innerHTML = '<option value="">보험회사명을 선택하세요</option>'; // 기본 옵션 추가
    codes.forEach(code => {
        const option = document.createElement('option');
        option.value = code.code; // 보험 회사 코드 설정
        option.textContent = `${code.name}`; // 보험 회사 이름 설정
        dropdown.appendChild(option);
    });
}

//======================================================================================================================

// 상품 검색 버튼 클릭 이벤트 핸들러
document.getElementById('searchProductBtn').addEventListener('click', () => {
    const insuranceCode = document.getElementById('insuranceCode').value; // 선택된 보험 회사 코드 가져오기
    const modal = new bootstrap.Modal(document.getElementById('productModal'));
    modal.show();

    fetch(`/db/products?insuranceCode=${insuranceCode}`)
        .then(response => response.json()) // 응답 데이터를 JSON으로 파싱
        .then(data => {
            renderProductList(data);
        })
        .catch(error => console.error('상품 데이터를 가져오는 중 오류 발생:', error)); // 오류 처리
});

// 상품 데이터를 렌더링하는 함수
function renderProductList(productData) {
    const list = document.getElementById('productList');
    list.innerHTML = ''; // 기존 데이터 초기화

    if (productData.length === 0) {
        list.innerHTML = '<li class="list-group-item">검색 결과가 없습니다.</li>'; // 검색 결과 없을 시 메시지 표시
        return;
    }

    // 상품 데이터를 기반으로 목록 생성
    productData.forEach(product => {
        const li = document.createElement('li');
        li.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'align-items-center');
        li.textContent = `${product.productName} (${product.productNo})`;
        li.addEventListener('click', () => {
            document.getElementById('productName').value = product.productName; // 선택한 상품 이름 설정
            const modal = bootstrap.Modal.getInstance(document.getElementById('productModal'));
            modal.hide(); // 모달 닫기
        });
        list.appendChild(li);
    });
}

//======================================================================================================================

// 등록 버튼 클릭으로 db 저장 생성하기

document.getElementById('registerContractBtn').addEventListener('click', function () {
    const contractData = {
        contractNo: document.getElementById('contractNo').value,
        contractDate: document.getElementById('contractDate').value,
        contractDuration: document.getElementById('contractDuration').value,
        eachPayment: document.getElementById('eachPayment').value,
        basicPayWith: document.getElementById('paymentMethod').value,
        paymentTerm: document.getElementById('paymentTerm').value,
        paymentDay: document.getElementById('paymentDate').value,
        insuredName: document.getElementById('insuredName').value,
        insuredSsn: document.getElementById('insuredSsn').value,
        otherMatters: document.getElementById('otherMatters').value,
        productNo: document.getElementById('productNo').value,
        customerNo: document.getElementById('customerName').dataset.customerNo,
        employeeNo: document.getElementById('employeeName').dataset.employeeNo
    };

    fetch('/db/contract', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(contractData)
    })
        .then(response => response.text())
        .then(message => alert(message))
        .catch(error => console.error('계약 등록 중 오류 발생:', error));
});

