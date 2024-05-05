$(document).ready(function() {
    $("#registrationForm").submit(function(event) {
        event.preventDefault();
        var email = $("#email").val();
        $.ajax({
            url: '/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({email: email}),
            success: function(response) {
                $("#message").html(response);
                $("#email").val('');
                setTimeout(function() {
                    window.location.href = '/login';
                }, 10);
            },
            error: function(response) {
                $("#message").html(response.responseText);
            }
        });
    });
});
