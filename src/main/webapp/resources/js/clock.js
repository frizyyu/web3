document.addEventListener("DOMContentLoaded", function() {
    function updateTime() {
        const xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const parser = new DOMParser();
                const doc = parser.parseFromString(xhr.responseText, "text/html");
                const bodyContent = doc.body.textContent.trim();
                const currentTimeElement = document.getElementById("form:currentTime");
                if (currentTimeElement) {
                    currentTimeElement.innerHTML = bodyContent;
                } else {
                    console.error("Element not found");
                }
            }
        };
        xhr.open("GET", "getCurrentTime.xhtml", true);
        xhr.send();
    }

    setInterval(updateTime, 1000); //заменить на 5к если что
    updateTime();
});