
function downloadFile(url, reportType) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', url, true);
    xhr.responseType = 'blob'; // Указываем, что ожидаемый ответ - Blob
    xhr.onload = function() {
        if (this.status === 200) {
            // Получаем имя файла из заголовка Content-Disposition
            var contentDisposition = xhr.getResponseHeader('Content-Disposition');
            var fileName = contentDisposition.match(/filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/)[1];
            // Удаляем префикс UTF-8''
            fileName = fileName.replace(/^UTF-8''/, '');
            // Декодируем имя файла
            fileName = decodeURIComponent(fileName);
            // Создаем URL для скачивания файла
            var fileUrl = window.URL.createObjectURL(this.response);
            // Создаем ссылку для скачивания
            var link = document.createElement('a');
            link.href = fileUrl;
            link.download = fileName; // Используем имя файла из заголовка
            document.body.appendChild(link);
            link.click(); // Инициируем скачивание
            document.body.removeChild(link); // Удаляем ссылку после скачивания
        } else {
            alert("Произошла ошибка при генерации документа. Пожалуйста, попробуйте еще раз.");
        }
    };

    // Создаем FormData и добавляем reportType в него
    var formData = new FormData();
    formData.append('reportType', reportType);

    xhr.send(formData); // Отправляем FormData в теле запроса
}

$(document).ready(function() {
    // Примеры кнопок с разными типами документов
    $("#generateButton1").click(function() {
        downloadFile('/documents/generate_practice_report', 'Бакалавриат_ПИиКН');
    });

    $("#generateButton2").click(function() {
        downloadFile('/documents/generate_practice_report', 'Магистратура_ТРПС');
    });

    $("#generateButton3").click(function() {
        downloadFile('/documents/generate_practice_report', 'Магистратура_КМиАД');
    });
    /////
    $("#generateButton4").click(function() {
        downloadFile('/documents/generate_individual_task_report', 'Бакалавриат_ПИиКН');
    });

    $("#generateButton5").click(function() {
        downloadFile('/documents/generate_individual_task_report', 'Магистратура_ТРПС');
    });

    $("#generateButton6").click(function() {
        downloadFile('/documents/generate_individual_task_report', 'Магистратура_КМиАД');
    });
    /////
    $("#generateButton7").click(function() {
        downloadFile('/documents/generate_practice_feedback_report', 'Бакалавриат_ПИиКН');
    });

    $("#generateButton8").click(function() {
        downloadFile('/documents/generate_practice_feedback_report', 'Магистратура_ТРПС');
    });

    $("#generateButton9").click(function() {
        downloadFile('/documents/generate_practice_feedback_report', 'Магистратура_КМиАД');
    });
    /////
    $("#generateButton10").click(function() {
        downloadFile('/documents/generate_application_for_practice_report', 'Бакалавриат_ПИиКН');
    });

    $("#generateButton11").click(function() {
        downloadFile('/documents/generate_application_for_practice_report', 'Магистратура_ТРПС');
    });

    $("#generateButton12").click(function() {
        downloadFile('/documents/generate_application_for_practice_report', 'Магистратура_КМиАД');
    });

});


