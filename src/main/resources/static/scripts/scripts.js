const titles = ["ФИО: ", "Должность: ", "Ученая степень: ", "Ученое звание: "];

function getStudent() {
    fetch('/getStudent')
        .then(response => response.json())
        .then(data => {
            updateAllStudentHeaders(data);
        });
}

function getSupervisor() {
    fetch('/getSupervisor')
        .then(response => response.json())
        .then(data => {
            updateSupervisorHeaders(data);
        });
}

function getSupervisorNSU() {
    fetch('/getSupervisorNSU')
        .then(response => response.json())
        .then(data => {
            updateSupervisorNSUHeaders(data);
        });
}

function getSupervisorOrg() {
    fetch('/getSupervisorOrg')
        .then(response => response.json())
        .then(data => {
            updateSupervisorOrgHeaders(data);
        });
}

function getCoSupervisor() {
    fetch('/getCoSupervisor')
        .then(response => response.json())
        .then(data => {
            updateCoSupervisorHeaders(data);
        });
}

function getConsultant() {
    fetch('/getConsultant')
        .then(response => response.json())
        .then(data => {
            updateConsultantHeaders(data);
        });
}

function getReviewer() {
    fetch('/getReviewer')
        .then(response => response.json())
        .then(data => {
            updateReviewerHeaders(data);
        });
}

function updateSupervisorHeaders(datas) {
    const data = datas[0];
    let headerIndex = 1;
    const keys = Object.keys(data);

    console.log(keys);
    var headerId = `supervisorNameHeader${headerIndex}`;
    var headerElement = document.getElementById(headerId);
    if (headerElement) {
        headerElement.textContent = `${data[keys[0]]} ${data[keys[1]]} ${data[keys[2]]}`;
        headerElement.style.display = 'block';
        console.log(headerElement.textContent);
        headerIndex++;
    } else {
        headerElement.style.display = 'none';
    }

    for (let i = headerIndex; i <= keys.length; i++) {
        var headerId = `supervisorNameHeader${i}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = data[keys[i + 1]];
            headerElement.style.display = 'block';
        } else {
            headerElement.style.display = 'none';
        }
    }
}

function updateSupervisorNSUHeaders(datas) {
    const data = datas[0];
    let headerIndex = 1;
    const keys = Object.keys(data);

    console.log(keys);
    var headerId = `supervisorNSUNameHeader${headerIndex}`;
    var headerElement = document.getElementById(headerId);
    if (headerElement) {
        headerElement.textContent = `${data[keys[0]]} ${data[keys[1]]} ${data[keys[2]]}`;
        headerElement.style.display = 'block';
                console.log(headerElement.textContent);

        headerIndex++;
    } else {
        headerElement.style.display = 'none';
    }

    for (let i = headerIndex; i <= keys.length; i++) {
        var headerId = `supervisorNSUNameHeader${i}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = data[keys[i + 1]];
            headerElement.style.display = 'block';
        } else {
            headerElement.style.display = 'none';
        }
    }
}

function updateSupervisorOrgHeaders(datas) {
    const data = datas[0];
    let headerIndex = 1;
    const keys = Object.keys(data);

    console.log(keys);
    var headerId = `supervisorOrgNameHeader${headerIndex}`;
    var headerElement = document.getElementById(headerId);
    if (headerElement) {
        headerElement.textContent = `${data[keys[0]]} ${data[keys[1]]} ${data[keys[2]]}`;
        headerElement.style.display = 'block';
        headerIndex++;
    } else {
        headerElement.style.display = 'none';
    }

    for (let i = headerIndex; i <= keys.length; i++) {
        var headerId = `supervisorOrgNameHeader${i}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = data[keys[i+1]];
            headerElement.style.display = 'block';
        } else {
            headerElement.style.display = 'none';
        }
    }
}

function updateCoSupervisorHeaders(datas) {
    const data = datas[0];
    let headerIndex = 1;
    const keys = Object.keys(data);

    console.log(keys);
    var headerId = `cosupervisorNameHeader${headerIndex}`;
    var headerElement = document.getElementById(headerId);
    if (headerElement) {
        headerElement.textContent = `${data[keys[0]]} ${data[keys[1]]} ${data[keys[2]]}`;
        headerElement.style.display = 'block';
        headerIndex++;
    } else {
        headerElement.style.display = 'none';
    }

    for (let i = headerIndex; i <= keys.length; i++) {
        var headerId = `cosupervisorNameHeader${i}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = data[keys[i+1]];
            headerElement.style.display = 'block';
        } else {
            headerElement.style.display = 'none';
        }
    }
}

function updateConsultantHeaders(datas) {
    const data = datas[0];
    let headerIndex = 1;
    const keys = Object.keys(data);

    console.log(keys);
    var headerId = `consultantNameHeader${headerIndex}`;
    var headerElement = document.getElementById(headerId);
    if (headerElement) {
        headerElement.textContent = `${data[keys[0]]} ${data[keys[1]]} ${data[keys[2]]}`;
        headerElement.style.display = 'block';
        headerIndex++;
    } else {
        headerElement.style.display = 'none';
    }

    for (let i = headerIndex; i <= keys.length; i++) {
        var headerId = `consultantNameHeader${i}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = data[keys[i+1]];
            headerElement.style.display = 'block';
        } else {
            headerElement.style.display = 'none';
        }
    }
}

function updateReviewerHeaders(datas) {
    const data = datas[0];
    let headerIndex = 1;
    const keys = Object.keys(data);

    console.log(keys);
    var headerId = `reviewerNameHeader${headerIndex}`;
    var headerElement = document.getElementById(headerId);
    if (headerElement) {
        headerElement.textContent = `${data[keys[0]]} ${data[keys[1]]} ${data[keys[2]]}`;
        headerElement.style.display = 'block';
        headerIndex++;
    } else {
        headerElement.style.display = 'none';
    }

    for (let i = headerIndex; i <= keys.length; i++) {
        var headerId = `reviewerNameHeader${i}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = data[keys[i+1]];
            headerElement.style.display = 'block';
        } else {
            headerElement.style.display = 'none';
        }
    }
}

function updateAllStudentHeaders(studentsData) {
    if (studentsData.length > 0) {
        const studentData = studentsData[0];
        let headerIndex = 1;
        const keys = Object.keys(studentData).map(Number);

        console.log(keys);
        var headerId = `studentNameHeader${headerIndex}`;
        var headerElement = document.getElementById(headerId);
        if (headerElement) {
            headerElement.textContent = `${studentData[keys[0]]} ${studentData[keys[1]]} ${studentData[keys[2]]}`;
            headerElement.style.display = 'block';
            headerIndex++;
        } else {
            headerElement.style.display = 'none';
        }

        for (let i = headerIndex; i <= keys.length; i++) {
            var headerId = `studentNameHeader${i}`;
            var headerElement = document.getElementById(headerId);
            if (headerElement) {
                if (i - 1 < keys.length) {
                    headerElement.textContent = studentData[keys[i + 1]];
                    headerElement.style.display = 'block';
                } else {
                    headerElement.style.display = 'none';
                }
            }
        }

        getSupervisor();
        getSupervisorNSU();
        getSupervisorOrg();
        getCoSupervisor();
        getConsultant();
        getReviewer();
    }
}


document.addEventListener("DOMContentLoaded", function() {
    getStudent();
});
