<%--
  Created by IntelliJ IDEA.
  User: hanwenzhang
  Date: 10/5/23
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>2-Step Verification</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="templates/header.jsp" />
    <h1 style="margin-top: 50px;">2-Step Verification</h1>
    <p>Please enter your username below.</p>
    <p>If we find your account, we will email you the code to login.</p>
    <form id="forgetForm2" style="margin-top: 2rem">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <input type="submit" value="Submit">
    </form>
    <jsp:include page="templates/footer.jsp" />
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsForgetPassword2.js"></script>
</html>
