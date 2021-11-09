<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign Up</title>
    <style>
        <%@include file="/resources/css/signup.css"%>
    </style>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&family=Oswald&display=swap" rel="stylesheet">
</head>
<body>
<div class="registration_block">
    <h1>Регистрация</h1>

    <form method="post" action="signUp" class="form">
        <input id="lastName" name="lastName" placeholder="Фамилия" class="button">
        <br>
        <br>
        <input id="firstName" name="firstName" placeholder="Имя" class="button">
        <br>
        <br>
        <input id="patronymic" name="patronymic" placeholder="Отчество" class="button">
        <br>
        <br>
        <input id="email" type="email" name="email" placeholder="Почта" class="button">
        <br>
        <br>
        <input id="password" type="password" name="password" placeholder="Пароль" class="button">
        <br>
        <br>
        <input id="password-repeat" type="password" name="password-repeat" placeholder="Повторите пароль" class="button">
        <br>
        <br>
        <div class="role_sector">
        <label for="role">Выберите роль</label>
        <select name="role" id="role" class="role_button">
            <option disabled>Выберите роль:</option>
            <option label="ученик" value="2">Ученик</option>
            <option label="учитель" value="1">Учитель</option>
        </select>
        </div>
        <div class="message">${message}</div>
        <br>
        <br>
        <input type="submit" value="Создать аккаунт" class="submit">
        <br>
        <p class="text">Уже есть аккаунт? <a href="<c:url value="/signIn"/>">Войти</a> <a href="<c:url value="/"/>">Назад</a></p>
    </form>
</div>



</body>
</html>
