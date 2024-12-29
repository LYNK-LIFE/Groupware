function submitForm() {
    const data = {
        customerName: document.getElementById('customerName').value,
        customerSsn: document.getElementById('customerSsn').value,
        customerMobile: document.getElementById('customerMobile').value,
        customerAddr: document.getElementById('customerAddr').value,
        customerEmail: document.getElementById('customerEmail').value
    };

    console.log(data);

    fetch('/db/customer', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => {
        console.log(response);
        if (response.ok) {
            alert('고객이 성공적으로 등록되었습니다.');
        } else {
            alert('오류 발생');
        }
    });
}