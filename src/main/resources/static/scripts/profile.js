$(document).ready(function() {
    $("#changePasswordForm").submit(function(event) {
        event.preventDefault();
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();
        $.ajax({
            url: '/change-password',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({newPassword: newPassword, confirmPassword: confirmPassword}),
            success: function(response) {
                $("#message").html(response);
                $("#newPassword").val('');
                $("#confirmPassword").val('');
                setTimeout(function() {
                    window.location.href = '/profile';
                }, 1500); // Задержка в 2000 миллисекунд (2 секунды)
            },
            error: function(response) {
                $("#message").html(response.responseText);
            }
        });
    });
});