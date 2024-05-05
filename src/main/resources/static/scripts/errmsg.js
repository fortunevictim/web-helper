document.getElementById('sendButton').addEventListener('click', function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы
    const errorText = document.getElementById('errorText').value;
    if (!errorText) {
        alert('Пожалуйста, введите сообщение об ошибке.');
        return;
    }
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/sendErrorMsg', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) { // Убедитесь, что запрос завершен
            if (xhr.status === 200) {
                console.log(xhr.responseText); // Выводим ответ сервера в консоль
                document.getElementById('responseMessage').innerText = xhr.responseText; // Отображаем ответ на странице
                alert('Сообщение отправлено.');
            } else {
                console.error('Ошибка отправки сообщения:', xhr.status);
                document.getElementById('responseMessage').innerText = 'Ошибка при отправке сообщения. Статус: ' + xhr.status; // Отображаем ошибку на странице
            }
        }
    };
    xhr.send(JSON.stringify({
        to: '',
        from: '',
        subject: '',
        message: errorText
    }));
});
