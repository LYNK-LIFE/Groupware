fetch('/db/top-sales')
    .then(response => response.json())
    .then(data => {

        const labels = data.map(item => item.employeeName || "Unknown");
        const sales = data.map(item => item.totalSales / 1000 || 0);
        const contractCounts = data.map(item => (item.contractCount || 0));

        const ctx = document.getElementById('salesChart');
        if (!ctx) {
            console.error('Canvas element not found');
            return;
        }

        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: '금액 (단위: 천원)',
                        data: sales,
                        backgroundColor: 'rgba(54, 162, 235, 0.7)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-sales'
                    },
                    {
                        label: '계약 건수',
                        data: contractCounts,
                        backgroundColor: 'rgba(255, 99, 132, 0.7)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1,
                        yAxisID: 'y-contracts'
                    }
                ]
            },
            options: {
                scales: {
                    yAxes: [
                        {
                            id: 'y-sales',
                            position: 'left',
                            ticks: {
                                beginAtZero: true,
                                callback: function (value) {
                                    return value.toLocaleString();
                                }
                            },
                            scaleLabel: {
                                display: true,
                                labelString: '금액 (단위: 천원)'
                            }
                        },
                        {
                            id: 'y-contracts',
                            position: 'right',
                            ticks: {
                                beginAtZero: true,
                                stepSize: 3,
                                callback: function (value) {
                                    // 3의 배수만 표시
                                    return value % 3 === 0 ? value + '건' : '';
                                }
                            },
                            scaleLabel: {
                                display: true,
                                labelString: '계약 건수'
                            }
                        }
                    ],
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        callbacks: {
                            label: function (tooltipItem) {
                                if (tooltipItem.datasetIndex === 0) {
                                    return tooltipItem.raw + '천원';
                                } else {
                                    return tooltipItem.raw + '건';
                                }
                            }
                        }
                    }
                }
            }
        });
    })
    .catch(error => console.error('Error fetching data:', error));
