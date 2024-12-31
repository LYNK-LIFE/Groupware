fetch('/db/top-sales')
    .then(response =>response.json())
    .then(data => {

        const labels = data.map(item => item.employeeName || "Unknown");
        const sales = data.map(item => item.totalSales || 0);

        const ctx = document.getElementById('salesChart');
        if(!ctx){
            console.error('Canvas element not found');
            return;
        }

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: '금액 (단위: 원)',
                    data: sales,
                    backgroundColor: 'rgba(54, 162, 235, 0.7)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    }
                }
            }
        });
    })
    .catch(error => console.error('Error fetching data:', error));

