async function fetchStudents(profile) {
    try {
        const response = await fetch(`/getStudentsWithEmailControl?profile=${encodeURIComponent(profile)}`);
        const data = await response.json();
        populateStudentList(data, 'studentList');

        const studentList = document.getElementById('studentList');
        const emptyOptionIndex = Array.from(studentList.options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            studentList.selectedIndex = emptyOptionIndex;
        }
    } catch (error) {
        console.error('Error in fetchStudents:', error);
    }
}


async function fetchProfiles() {
    try {
        const data = [
            { profile: "Учащиеся" },
            { profile: "Бакалавриат" },
            { profile: "Магистратура" }
        ];
        populateStudentProfileList(data, 'studentProfileList');
    } catch (error) {
        console.error('Error in fetchProfiles:', error);
    }
}


async function fetchSupervisors() {
    const urls = [
        ['/getSupervisorControl', 'supervisorsList'],
        ['/getCoSupervisorControl', 'coSupervisorsList'],
        ['/getConsultantControl', 'consultantList'],
        ['/getReviewerControl', 'reviewerList'],
        ['/getPracticeSupervisorNSUControl', 'practiceSupervisorNSUList'],
        ['/getPracticeSupervisorOrgControl', 'practiceSupervisorOrgList']
    ];

    for (const [url, listName] of urls) {
        try {
            const response = await fetch(url);
            const data = await response.json();
            populateListWithFio(data, listName);
        } catch (error) {
            console.error(`Error in fetchSupervisors for ${url}:`, error);
        }
    }
}

function populateStudentProfileList(data, listName) {
    const profileList = document.getElementById(listName);
    profileList.innerHTML = '';
    data.forEach(profileInfo => {
        const profileName = profileInfo.profile;
        console.log(profileName);
        const option = document.createElement('option');
        option.value = profileName;
        option.textContent = profileName;

        profileList.appendChild(option);
    });
}

function populateStudentList(data, listName) {
    const studentList = document.getElementById(listName);
    studentList.innerHTML = '';
    data.forEach(studentInfo => {
        //console.log(studentInfo);
        const fullName = studentInfo.fullName;
        const email = studentInfo.email;

        const option = document.createElement('option');
        option.value = fullName;
        option.textContent = fullName;
        option.dataset.email = email;

        studentList.appendChild(option);
    });
    const emptyOption = document.createElement('option');
    emptyOption.value = '';
    emptyOption.textContent = 'Не выбрано';

    studentList.appendChild(emptyOption);
}

function populateListWithFio(data, listName) {
    const nlist = document.getElementById(listName);
    nlist.innerHTML = '';
    data.forEach(info => {
        const fullName = info.fullName;
        console.log(listName + " : " + info);
        const option = document.createElement('option');
        option.value = fullName;
        option.textContent = fullName;

        nlist.appendChild(option);
    });

    const emptyOption = document.createElement('option');
    emptyOption.value = '';
    emptyOption.textContent = 'Не выбрано';
    nlist.appendChild(emptyOption);

    nlist.selectedIndex = nlist.options.length - 1;
}


/////////////////////////////////////////////////////////////////////////

async function sendSelectedStudent() {
    const selectedOption = document.getElementById('studentList').options[document.getElementById('studentList').selectedIndex];
    const email = selectedOption.dataset.email;
    const oldEmail = selectedOption.dataset.email;
    await fetchTextFields(email);
    await fetchStudentInfo(email);
}

async function sendSelectedProfile() {

}
/////////////////////////////////////////////////////////////////////////

async function fetchStudentInfo(email) {
    try {
        const response = await fetch(`/getStudentCurrentInfoControl?email=${encodeURIComponent(email)}`);
        const data = await response.json();
        console.log(data[0]);
        // Предполагаем, что data[0] содержит все необходимые поля, и если они отсутствуют, они будут null или пустыми строками
        const supervisor = data[0].supervisor || '';
        const coSupervisor = data[0].coSupervisor || '';
        const consultant = data[0].consultant || '';
        const reviewer = data[0].reviewer || '';
        const practice_sup_NSU = data[0].practice_sup_NSU || '';
        const practice_sup_org = data[0].practice_sup_org || '';

        updateSupervisorLists(supervisor, coSupervisor, consultant, reviewer, practice_sup_NSU, practice_sup_org);
    } catch (error) {
        console.error('Error in fetchStudentInfo:', error);
    }
}


function updateSupervisorLists(supervisor, coSupervisor, consultant, reviewer, practice_sup_NSU, practice_sup_org) {
    const lists = ['supervisorsList', 'coSupervisorsList', 'consultantList', 'reviewerList', 'practiceSupervisorNSUList', 'practiceSupervisorOrgList'];
    const listsElements = lists.map(listName => document.getElementById(listName));

    if (supervisor && supervisor.trim()!=='') {
        const supervisorIndex = Array.from(listsElements[0].options).findIndex(option => option.value === supervisor);
        if (supervisorIndex!== -1) {
            listsElements[0].selectedIndex = supervisorIndex;
        }
    } else {
        const emptyOptionIndex = Array.from(listsElements[0].options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            listsElements[0].selectedIndex = emptyOptionIndex;
        }
    }

    if (coSupervisor && coSupervisor.trim()!== '') {
        const coSupervisorIndex = Array.from(listsElements[1].options).findIndex(option => option.value === coSupervisor);
        if (coSupervisorIndex!== -1) {
            listsElements[1].selectedIndex = coSupervisorIndex;
        }
    } else {
        const emptyOptionIndex = Array.from(listsElements[1].options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            listsElements[1].selectedIndex = emptyOptionIndex;
        }
    }

    if (consultant && consultant.trim()!== '') {
        const consultantIndex = Array.from(listsElements[2].options).findIndex(option => option.value === consultant);
        if (consultantIndex!== -1) {
            listsElements[2].selectedIndex = consultantIndex;
        }
    } else {
        const emptyOptionIndex = Array.from(listsElements[2].options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            listsElements[2].selectedIndex = emptyOptionIndex;
        }
    }

    if (reviewer && reviewer.trim()!== '') {
        const reviewerIndex = Array.from(listsElements[3].options).findIndex(option => option.value === reviewer);
        if (reviewerIndex!== -1) {
            listsElements[3].selectedIndex = reviewerIndex;
        }
    } else {
        const emptyOptionIndex = Array.from(listsElements[3].options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            listsElements[3].selectedIndex = emptyOptionIndex;
        }
    }

    if (practice_sup_NSU && practice_sup_NSU.trim()!== '') {
        const practiceSupervisorNSUIndex = Array.from(listsElements[4].options).findIndex(option => option.value === practice_sup_NSU);
        if (practiceSupervisorNSUIndex!== -1) {
            listsElements[4].selectedIndex = practiceSupervisorNSUIndex;
        }
    } else {
        const emptyOptionIndex = Array.from(listsElements[4].options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            listsElements[4].selectedIndex = emptyOptionIndex;
        }
    }

    if (practice_sup_org && practice_sup_org.trim()!== '') {
        const practiceSupervisorOrgIndex = Array.from(listsElements[5].options).findIndex(option => option.value === practice_sup_org);
        if (practiceSupervisorOrgIndex!== -1) {
            listsElements[5].selectedIndex = practiceSupervisorOrgIndex;
        }
    } else {
        // Если practice_sup_org отсутствует, устанавливаем selectedIndex в "пустое" значение
        const emptyOptionIndex = Array.from(listsElements[5].options).findIndex(option => option.value === '');
        if (emptyOptionIndex!== -1) {
            listsElements[5].selectedIndex = emptyOptionIndex;
        }
    }
}

/////////////////////////////////////////////////////////////////////////
async function fetchTextFields(email) {
    try {
        const response = await fetch(`/getStudentTextDataControl?email=${encodeURIComponent(email)}`);
        const data = await response.json();
        if (data[0] && data[0].fio && data[0].group && data[0].thesis && data[0].email
         && data[0].practiceOrder && data[0].thesisOrder && data[0].internshipPlace
         && data[0].internshipPlaceFull && data[0].internshipPlaceShort && data[0].profile
           ) {
                updateTextFields(data[0].fio, data[0].group, data[0].thesis, data[0].email,
                data[0].practiceOrder, data[0].thesisOrder, data[0].internshipPlace,
                data[0].internshipPlaceFull, data[0].internshipPlaceShort, data[0].profile);
             }
        } catch (error) {
            console.error('Error in fetchTextFields: ', error);
        }
}

function updateTextFields(fio, group, thesis, email, practiceOrder, thesisOrder,
                            internshipPlace, internshipPlaceFull, internshipPlaceShort, profile){
    const textFields = ['studentFio', 'studentGroup', 'thesis', 'studentEmail', 'practiceOrder', 'thesisOrder',
                        'internshipPlaceShort', 'internshipPlaceFull', 'internshipPlace', 'studentProfile'];
    const textFieldsElements = textFields.map(field => document.getElementById(field));
    textFieldsElements[0].value = fio;
    textFieldsElements[1].value = group;
    textFieldsElements[2].value = thesis;
    textFieldsElements[3].value = email;
    textFieldsElements[4].value = practiceOrder;
    textFieldsElements[5].value = thesisOrder;
    textFieldsElements[6].value = internshipPlaceShort;
    textFieldsElements[7].value = internshipPlaceFull;
    textFieldsElements[8].value = internshipPlace;
    textFieldsElements[9].value = profile;
}

function getStudentGroup(email) {
    console.log("getStudentGroup js called");
    fetch(`/getStudentControl?email=${encodeURIComponent(email)}`)
  .then(response => response.json())
  .then(data => {
        const group = data[0].group;
        document.getElementById('studentGroup').value = group;
        console.log("Group: " + group);
    })
  .catch(error => console.error('Error:', error));
}


/////////////////////////////////////////////////////////////////////////
document.getElementById('collectDataButton').addEventListener('click', async function() {
    const studentList = document.getElementById('studentList');
    const selectedStudent = studentList.value;

    if (!selectedStudent) {
        alert('Пожалуйста, выберите студента перед сбором данных.');
        return;
    }

    try {
        const supervisor = document.getElementById('supervisorsList').value;
        const coSupervisor = document.getElementById('coSupervisorsList').value;
        const practice_sup_NSU = document.getElementById('practiceSupervisorNSUList').value;
        const practice_sup_org = document.getElementById('practiceSupervisorOrgList').value;
        const consultant = document.getElementById('consultantList').value;
        const reviewer = document.getElementById('reviewerList').value;

        const studentFio = document.getElementById('studentFio').value;
        const studentGroup = document.getElementById('studentGroup').value;
        const thesis = document.getElementById('thesis').value;
        const email = document.getElementById('studentEmail').value;
        const oldEmail = document.getElementById('studentList').options[document.getElementById('studentList').selectedIndex].dataset.email; // Неизмененный email

        const practiceOrder = document.getElementById('practiceOrder').value;
        const thesisOrder = document.getElementById('thesisOrder').value;
        const internshipPlaceShort = document.getElementById('internshipPlaceShort').value;
        const internshipPlaceFull = document.getElementById('internshipPlaceFull').value;
        const internshipPlace = document.getElementById('internshipPlace').value;
        const profile = document.getElementById('studentProfile').value;

        const data = {
            studentFio: studentFio,
            studentGroup: studentGroup,
            supervisor: supervisor,
            coSupervisor: coSupervisor,
            thesis: thesis,
            email: email,
            oldEmail: oldEmail,
            studentGroup: studentGroup,
            practice_sup_NSU: practice_sup_NSU,
            practice_sup_org: practice_sup_org,
            practiceOrder: practiceOrder,
            thesisOrder: thesisOrder,
            consultant: consultant,
            reviewer: reviewer,
            internshipPlaceShort: internshipPlaceShort,
            internshipPlaceFull: internshipPlaceFull,
            internshipPlace: internshipPlace,
            profile: profile
        };

        const response = await fetch('/submitStudentData', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        const result = await response.text();
        console.log(result);
        location.reload();
    } catch (error) {
        console.error('Error in submitStudentData:', error);
    }
});


document.addEventListener("DOMContentLoaded", async function() {
    console.log("before fetchStudents()");
    await fetchProfiles();
    await fetchStudents('Учащиеся'); //default
    await fetchSupervisors();
});

document.getElementById('studentProfileList').addEventListener('change', async function() {
    const selectedProfile = this.value;
    await fetchStudents(selectedProfile);
});