$(document).ready(function() {
    $("#loginForm").submit(function(event) {
        event.preventDefault();
        var email = $("#email").val();
        var password = $("#password").val();
        $.ajax({
            url: '/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({email: email, password: password}),
            success: function(response) {
                $("#message").html(response);
                $("#email").val('');
                $("#password").val('');
                setTimeout(function() {
                    window.location.href = '/';
                }, 300); // Задержка в 2000 миллисекунд (2 секунды)
            },
            error: function(response) {
                $("#message").html(response.responseText);
            }
        });
    });

    $("#forgotPasswordButton").click(function() {
        window.location.href = '/forgot-password';
    });

});
