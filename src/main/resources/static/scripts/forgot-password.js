$(document).ready(function() {
    $("#forgotPasswordForm").submit(function(event) {
        event.preventDefault();
        var email = $("#email").val();
        $.ajax({
            url: '/forgot-password', // Измененный URL для обработки запроса смены пароля
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({email: email}),
            success: function(response) {
                $("#message").html(response);
                $("#email").val('');
            },
            error: function(response) {
                $("#message").html(response.responseText);
            }
        });
    });
});
