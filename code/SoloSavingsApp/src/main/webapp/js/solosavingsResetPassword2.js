function setJwtInCookie(jwtResponse) {
    console.log("Token: " + jwtResponse['token'] + " Expiry: " + jwtResponse['expiry']);
    document.cookie = "jwtToken=" + jwtResponse['token'] + "; expires=" + jwtResponse['expiry'];
}

function isValidationCheckPassed() {
    const password = document.getElementById("password").value;
    //const confirmPassword = document.getElementById("confirm_password").value;
    //const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

/*    if (!passwordPattern.test(password)) {
        alert("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.");
        return false;
    }*/

/*    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }*/

    return true;
}

$(document).ready(function() {
    $('#resetForm2').submit(function(event) {
        event.preventDefault();
        if(!isValidationCheckPassed()) return;
        const formData = {
            token: $('input[name="token"]').val(),
            username: $('input[name="username"]').val(),
            password: $('input[name="password"]').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/reset-password2',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Reset password successfully:', response);
                setJwtInCookie(response);
                alert("Authentication passed successfully, redirect to your dashboard.");
                window.location.replace("/solosavings/dashboard");
            },
            error: function(error) {
                alert('Something went wrong, please try again.');
                console.error('Something went wrong:', error);
            }
        });
    });
});