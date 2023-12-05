<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SoloSavings - Login</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <jsp:include page="templates/header.jsp" />

    <h1 style="margin-top: 50px;">Login to SoloSavings</h1>

    <section>
        <form id="loginForm" >
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            <span id="usernameError" class="error"></span>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            <span id="passwordError" class="error"></span>
            
            <button type="button" onclick="attemptLogin()">Login</button>

        </form>
    </section>

    <section id="otpSection" >
        <form id="" action="" method="post">
            <%--<label for="otp">Enter OTP:</label>
            <input type="text" id="otp" name="otp" required>--%>
            <%--<button type="submit">Login with OTP</button>--%>
            <button type="button" onclick="sendOTP()">Login with OTP</button>
        </form>
    </section>

    <section>
        <p>Don't have an account? <a href="/solosavings/register">Register here</a></p>
        <p>Forget your password? <a href="/solosavings/forget-password">Click here</a></p>
    </section>

    <jsp:include page="templates/footer.jsp" />
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsLogin.js"></script>
<script>
    function validateLoginForm() {
        // Validate Username
        var username = $("#username").val();
        if (!isValidUsername(username)) {
            $("#usernameError").text("Invalid username. Avoid special characters and spaces.");
            return false;
        } else {
            $("#usernameError").text("");
        }

        // Validate Password
        var password = $("#password").val();
        if (!isValidPassword(password)) {
            $("#passwordError").text("Invalid password. Password must be strong and at least 12 characters long.");
            return false;
        } else {
            $("#passwordError").text("");
        }

        return true;
    }



    function isValidUsername(username) {
        // Avoid special characters and spaces
        var regex = /^[a-zA-Z0-9]+$/;
        return regex.test(username);
    }

    function isValidPassword(password) {
        // Ensure it's strong (contains letters, numbers, and special characters, minimum lenght 12)
        var regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{12,}$/;
        return regex.test(password);
    }

    function attemptLogin() {
        if (validateLoginForm()) {
            var username = $("#username").val();
            var password = $("#password").val();

            $.ajax({
                type: 'POST',
                url: '/api/login',
                contentType: 'application/json',
                data: JSON.stringify({ username: username, password: password }),
                success: function(response) {
                    console.log('Login successful:', response);
                    setJwtInCookie(response);
                    alert("Authentication passed successfully, redirect to your dashboard.");
                    window.location.replace("/solosavings/dashboard");
                },
                error: function(error) {
                    alert('Invalid username or password. Please try again.');
                    console.error('Login error:', error);
                }
            });
        }
    }

    function sendOTP() {
            // Get the username from the form
            var username = $("#username").val();
            //alert(username);
            // Create a form element
            window.location.href = "/solosavings/forget-password2";

            //$("#otpSection").show();

    }
</script>
</html>
