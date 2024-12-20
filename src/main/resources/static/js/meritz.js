async function registerProduct() {
    const formData = {
        productCategory: parseInt(document.getElementById('productCategory').value),
        productNo: document.getElementById('productNo').value,
        productName: document.getElementById('productName').value,
    };

    // 입력값 검증
    if (!formData.productCategory || !formData.productNo || !formData.productName) {
        alert('모든 필드를 입력해주세요!');
        return;
    }

    const response = await fetch('/db/meritz', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
    });

    if (!response.ok) {
        console.error('Failed to register product:', response.statusText);
        return;
    }
    console.log('Product registered successfully');
    loadProducts();
}

async function loadProducts() {
    const response = await fetch('/db/meritz/products'); // JSON 데이터를 반환하는 엔드포인트
    if (!response.ok) {
        console.error('Failed to load products:', response.statusText);
        return;
    }

    const products = await response.json();
    const productList = document.getElementById('productList');
    productList.innerHTML = '';

    products.forEach(product => {
        const li = document.createElement('li');
        li.textContent = `No: ${product.productNo}, Name: ${product.productName}, Category: ${product.productCategory}`;
        productList.appendChild(li);
    });
}

loadProducts();
