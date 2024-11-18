document.addEventListener('DOMContentLoaded', function() {
    /*document.querySelector('input[type="submit"]').addEventListener('click', async function (event) {
        event.preventDefault();
        await sleep(900);
        loadData();
    });

    document.getElementById('mainForm:submit-hidden-btn').addEventListener('click', async function(event) {
        event.preventDefault();
        await sleep(900);
        loadData();
    });*/

    document.getElementById('mainForm:r').addEventListener('change', function() {
        loadData();
    });

    document.getElementById('mainForm:y').addEventListener('input', function(event) {
        const inputValue = event.target.value;
        const isValid = /^[-]?(\d+(\.\d*)?|\.\d+)$/.test(inputValue);
        const numberValue = parseFloat(inputValue);
        const isInRange = !isNaN(numberValue) && numberValue >= -3 && numberValue <= 5;

        if (!isValid || !isInRange) {
            event.target.classList.add('invalid');
        } else {
            event.target.classList.remove('invalid');
        }
    });
    const style = document.createElement('style');
    style.textContent = `
        .invalid {
            border: 2px solid red;
        }
    `;
    document.head.appendChild(style);
});

function handleTableUpdate() {
    loadData();
}

function loadData() {
    clearPoints();
    const table = document.querySelector('#mainForm\\:res-table');

    const rows = table.getElementsByTagName('tr');
    const r = document.getElementById('mainForm:r').value;

    for (let i = 1; i < rows.length; i++) {
        const row = rows[i];
        const cells = row.getElementsByTagName('td');
        if (cells.length !== 0) {
            const x = cells[0].textContent;
            const y = cells[1].textContent;
            const result = cells[3].textContent;
            drawPoint(x, y, r, result);
        }
    }
}

async function loadDataOnLoad() {
    await sleep(500);
    const table = document.querySelector('#mainForm\\:res-table');

    const rows = table.getElementsByTagName('tr');
    const r = document.getElementById('mainForm:r').value;
    for (let i = 1; i < rows.length; i++) {
        const row = rows[i];
        const cells = row.getElementsByTagName('td');
        if (cells.length !== 0) {
            const x = cells[0].textContent;
            const y = cells[1].textContent;
            const result = cells[3].textContent;
            if (x !== "" && y !== "") {
                drawPoint(x, y, r, result);
            }
            else
                drawPoint(x, y, r, "NaN");
        }
    }
}

function drawPoint(x, y, r, result) {
    let rn;
    if (result === "NaN") {
        rn = 0;
    } else {
        rn = 5;
    }
    const svg = document.getElementById('graph');
    const point = document.createElementNS('http://www.w3.org/2000/svg', 'circle');

    x = parseFloat(x);
    y = parseFloat(y);
    r = parseFloat(r);

    const scaledX = 300 + x * (200 / r);
    const scaledY = 300 - y * (200 / r);
    if (!isNaN(scaledY) && !isNaN(scaledX)) {
        point.setAttribute("cx", String(scaledX));
        point.setAttribute("cy", String(scaledY));
        point.setAttribute("r", rn);
        point.setAttribute('fill', result.trim() === '✅' ? 'green' : 'red');

        svg.appendChild(point);
    }
}

function clearPoints() {
    const svg = document.getElementById('graph');
    const points = svg.querySelectorAll('circle');
    points.forEach(point => svg.removeChild(point));
}

function handleGraphClick(event) {
    const svg = event.target.closest('svg');
    const rect = svg.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const r = parseFloat(document.getElementById('mainForm:r').value);

    const normalizedX = (x - 300) * (r / 200);
    const normalizedY = (300 - y) * (r / 200);

    const lastY = document.getElementById('mainForm:y').value;
    document.getElementById('mainForm:y').value = "0";

    document.getElementById('mainForm:hidden-y').value = String(normalizedY);
    document.getElementById('mainForm:hidden-x').value = String(normalizedX);

    document.getElementById('mainForm:submit-hidden-btn').click();
    document.getElementById('mainForm:y').value = lastY;
    loadData();
}

window.onload = loadDataOnLoad();





















function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}