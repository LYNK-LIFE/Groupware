document.addEventListener("DOMContentLoaded", () => {
    const menuItems = document.querySelectorAll(".menuContainer li > a");

    menuItems.forEach((menuItem) => {
        menuItem.addEventListener("click", (e) => {
            const subMenu = menuItem.nextElementSibling; // 하위 메뉴 (ul 요소)

            if (subMenu && subMenu.classList.contains("subMenu")) {
                e.preventDefault(); // 서브 메뉴가 있을 경우에만 기본 동작 차단!
                const isOpen = subMenu.style.height && subMenu.style.height !== "0px"; // 현재 열림 상태 확인

                if (isOpen) {
                    // 선택된 메뉴 닫기
                    subMenu.style.height = "0px";
                } else {
                    // 기존 열려 있는 하위 메뉴 닫기
                    document.querySelectorAll(".subMenu").forEach((menu) => {
                        menu.style.height = "0px";
                    });

                    // 선택된 메뉴 열기
                    subMenu.style.height = `${subMenu.scrollHeight}px`; // 콘텐츠 크기만큼 열기
                }
            }
        });
    });
});