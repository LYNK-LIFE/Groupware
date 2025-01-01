document.getElementById('nameSearchButton').addEventListener('click', () => {
    const name = document.getElementById('nameSearch').value;
    fetchContracts({ name });
});

document.getElementById('plannerSearchButton').addEventListener('click', () => {
    const plannerName = document.getElementById('plannerSearch').value;
    fetchContracts({ plannerName});
});

function fetchContracts(params) {
    const query = new URLSearchParams(params).toString(); //params를 퀴리 문자열로 변환
    const url = `/db/inquiry/json?${query}`;

    fetch(url)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error!status : ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.getElementById('contractTableBody');
            if(!Array.isArray(data)) {
                console.error('Unexpected data format : ', data);
                return;
            }
            // 데이터로 테이블
            tbody.innerHTML = data.map(contract => `
                <tr onclick="redirectToContract('${contract.contractNo}')">
                    <td>${contract.contractMngNo || '-'}</td>
                    <td>${contract.customerName || '-'}</td>
                    <td>${contract.insuredName || '-'}</td>
                    <td>${contract.insuranceCompany || '-'}</td>
                    <td>${contract.productName || '-'}</td>
                    <td>${contract.contractNo || '-'}</td>
                    <td>${contract.employeeName || '-'}</td>
                </tr>
            `).join('');
        })
        .catch(error => {
            console.error('Fetch error:', error); // 네트워크 또는 응답 관련 에러 처리
        });
}

// 테스트 예제: 동적으로 요청 파라미터 전달
fetchContracts({
    name: 'test',
    plannerName: 'John'
});

//클릭 이벤트 함수 구현
function redirectToContract(contractNo) {
    if (contractNo) {
        window.location.href = '/db/contract/details/${contractNo}`;';
    }else{
        alert("계약 정보를 찾을 수 없습니다.")
    }
}