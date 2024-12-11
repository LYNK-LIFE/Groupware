document.addEventListener("DOMContentLoaded", () => {
    const menuItems = document.querySelectorAll(".humanMenu > a"); // 메뉴 클릭 요소

    menuItems.forEach((menuItem) => {
        menuItem.addEventListener("click", (e) => {
            e.preventDefault(); // 기본 동작 차단
            const subMenu = menuItem.nextElementSibling; // 하위 메뉴 UL
            const isOpen = subMenu.style.height !== "0px"; // 현재 상태 확인

            if (isOpen) {
                // 닫기
                subMenu.style.height = "0px";
            } else {
                // 기존 열려 있는 하위 메뉴 닫기
                document.querySelectorAll(".subMenu").forEach((menu) => {
                    menu.style.height = "0px";
                });

                // 선택한 메뉴 열기
                subMenu.style.height = `${subMenu.scrollHeight}px`; // 콘텐츠 크기만큼 열기
            }
        });
    });
});