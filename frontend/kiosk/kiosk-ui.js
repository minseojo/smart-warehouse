function updateTotal() {
    let total = 0;
    document.querySelectorAll('.menu-item').forEach(item => {
      const price = parseInt(item.dataset.price);
      const qty = parseInt(item.querySelector('.qty').textContent);
      total += price * qty;
    });
    document.getElementById('totalPrice').textContent = total.toLocaleString();
}
  
function increase(button) {
    const qtyEl = button.parentNode.querySelector('.qty');
    qtyEl.textContent = parseInt(qtyEl.textContent) + 1;
    updateTotal();
}
  
function decrease(button) {
    const qtyEl = button.parentNode.querySelector('.qty');
    const current = parseInt(qtyEl.textContent);
    if (current > 0) {
      qtyEl.textContent = current - 1;
      updateTotal();
    }
}
  

function cancelOrder() {
    // 모든 수량 0으로 초기화
    document.querySelectorAll('.menu-item').forEach(item => {
      item.querySelector('.qty').textContent = '0';
    });
    
    console.log('주문 초기화');
    // 총합 업데이트
    updateTotal();
}

function confirmOrder() {
    const order = [];
    document.querySelectorAll('.menu-item').forEach(item => {
      const name = item.dataset.name;
      const qty = parseInt(item.querySelector('.qty').textContent);
      if (qty > 0) {
        order.push({ name, qty });
      }
    });
    console.log('주문 완료:', order);
    alert('주문이 접수되었습니다! 로봇이 이동합니다.');
    // TODO: postMessage or robot 연동
}
  
document.getElementById('orderBtn').addEventListener('click', () => {
    document.getElementById('kiosk').classList.toggle('open');
});


