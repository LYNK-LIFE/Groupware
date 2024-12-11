//
//     // const modalPage = document.querySelector('#myModal');
//
//     const successMSG = '[[${successMessage}]]';
//     console.log("successMSG : ", successMSG);
//
//     if (successMSG && successMSG.trim() !== '') {
//     // 모달을 초기화하고 보여주기
//     const myModal = new bootstrap.Modal(document.getElementById('myModal'));
//     myModal.show();
// }

document.addEventListener("DOMContentLoaded", () => {
    const modalElement = document.getElementById("myModal");

    if (modalElement) {
        const successMessageElement = document.getElementById("successMessage");
        if (successMessageElement) {
            const successMessage = successMessageElement.textContent.trim();
            if (successMessage) {
                const modalMessage = document.getElementById("modalMessage");
                modalMessage.textContent = successMessage;

                // Bootstrap Modal 객체 생성 (disableAutoFocus 옵션 추가)
                const myModal = new bootstrap.Modal(modalElement, {
                    keyboard: true,         // ESC 키로 닫기 허용
                    focus: true,            // 기본 포커스 동작 유지
                    disableAutoFocus: true  // 모달 열릴 때 포커스 자동 이동 비활성화
                });

                // 모달 열기
                myModal.show();

                // 모달이 열릴 때 포커스 관리 (수동)
                modalElement.addEventListener("show.bs.modal", () => {
                    document.body.setAttribute("inert", "true"); // 외부 요소 접근 차단
                });

                // 모달이 닫힐 때 외부 포커스 복원 및 inert 제거
                modalElement.addEventListener("hidden.bs.modal", () => {
                    document.body.removeAttribute("inert");

                    // 수동으로 트리거 버튼에 포커스 복원
                    const triggerButton = document.querySelector('[data-bs-toggle="modal"]') || document.querySelector("button");
                    if (triggerButton) {
                        setTimeout(() => triggerButton.focus(), 0); // 트리거 버튼에 포커스 복원
                    }
                });
            }
        }
    }
});


//     // 이메일 도메인 선택 시 "직접 입력" 활성화
//     document.getElementById('emailDomain').addEventListener('change', function () {
//     const customEmailDomain = document.getElementById('customEmailDomain');
//     if (this.value === "직접 입력") {
//     customEmailDomain.style.display = "inline-block";
//     customEmailDomain.focus();
// } else {
//     customEmailDomain.style.display = "none";
//     customEmailDomain.value = "";
// }
// });
//     function combineEmail() {
//     const emailName = document.getElementById('emailName').value;
//     const emailDomain = document.getElementById('emailDomain').value;
//     const customDomain = document.getElementById('customEmailDomain').value;
//
//     // 도메인이 "직접 입력"일 경우 커스텀 도메인 사용
//     const fullEmail = emailName + "@" + (emailDomain === "직접 입력" ? customDomain : emailDomain);
//
//     // hidden input에 저장
//     document.getElementById('fullEmail').value = fullEmail;
//     console.log("전체 이메일:", fullEmail);
// }
