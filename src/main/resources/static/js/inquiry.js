document.getElementById('nameSearchButton').addEventListener('click', () => {
    const name = document.getElementById('nameSearch').value;
    fetchContracts({ name });
});

document.getElementById('plannerSearchButton').addEventListener('click', () => {
    const plannerName = document.getElementById('plannerSearch').value;
    const plannerId = document.getElementById('plannerIdSearch').value;
    fetchContracts({ plannerName, plannerId });
});

function fetchContracts(params) {
    const query = new URLSearchParams(params).toString();
    fetch(`/db/contracts?${query}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('contractTableBody');
            tbody.innerHTML = data.map(contract => `
                <tr>
                    <td>${contract.id}</td>
                    <td>${contract.contractor}</td>
                    <td>${contract.insured}</td>
                    <td>${contract.insuranceCompany}</td>
                    <td>${contract.product}</td>
                    <td>${contract.contractNumber}</td>
                    <td>${contract.planner}</td>
                </tr>
            `).join('');
        });
}
