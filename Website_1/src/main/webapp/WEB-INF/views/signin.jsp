<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Sign In</title>
    <style>
        <%@include file="/resources/css/signup.css"%>
    </style>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Oswald&display=swap" rel="stylesheet">
</head>
<body>
<div class="signin">
    <h1>Вход в аккаунт</h1>

    <form method="post" action="signIn" class="form">
        <input id="email" type="email" name="email" placeholder="Email" class="button">
        <br>
        <br>
        <input id="password" type="password" name="password" placeholder="Пароль" class="button">
        <div class="message">${message}</div>
        <br>
        <br>
        <input type="submit" value="Войти" class="submit_signin">
        <br>
        <br>
        <p class="text">Еще нет аккаунта? <a href="<c:url value="/signUp"/>">Зарегистрироваться</a></p>
        <br>
        <p class="back"><a href="<c:url value="/"/>">Назад</a></p>
    </form>
</div>

</body>
</html>
