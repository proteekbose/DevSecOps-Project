function isValidationCheckPassed() {
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password_hash").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{12,}$/;

    if (!isValidUsername(username) && username.length < 6) {
        alert("Invalid username. Avoid special characters and spaces and length should be greater than 6");
        return false;
    }

    if (!isValidEmail(email)) {
        alert("Invalid email");
        return false;
    }

    if (!passwordPattern.test(password)) {
        alert("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 12 characters long.");
        return false;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }

    return true;
}

function isValidUsername(username) {
    // Avoid special characters and spaces
    var regex = /^[a-zA-Z0-9]+$/;
    return regex.test(username);
}

function isValidEmail(email) {
    // Simple email validation
    var regex = /\S+@\S+\.\S+/;
    return regex.test(email);
}

$(document).ready(function() {
    $('#registrationForm').submit(function(event) {
        event.preventDefault();
        if(!isValidationCheckPassed()) return;

        const formData = {
            username: $('input[name="username"]').val().toLowerCase(),
            password_hash: $('input[name="password_hash"]').val(),
            email: $('input[name="email"]').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/register',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Registration successful:', response);
                confirm("Your account successfully created, redirect to your dashboard.")
                window.location.replace("login");
            },
            error: function(error) {
                alert(error.responseText);
                console.error('Registration failed:', error);
            }
        });
    });
});