<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Редактировать">
    <%@include file="header.jsp" %>
    <div class="page">
        <div class="information">
            <div class="title_tasks">
                Редактировать студентов
                <div class="line_tasks"></div>
            </div>
            <br>
            <br>
            <div><a class="back_link" href="<c:url value="/profile"/>">Назад</a></div>
            <form action="edit" method="post" class="students_form">
                <label class="edit_label" for="deleteStudents">Выберите учеников, которых хотите удалить</label><br>
                <select multiple name="deleteStudents" id="deleteStudents">
                    <c:forEach items="${students}" var="student">
                    <option label="ученик" value="${student.email}">${student.number} ${student.lastName} ${student.firstName} ${student.patronymic}</option>
                    </c:forEach>
                </select>
                <br>
                <br>
                <label class="edit_label" for="addStudents">Введите через пробел email учеников, которых хотите добавить</label><br>
                <input id="addStudents" name="addStudents" class="input_students">
                <br>
                <br>
                <div class="message">${message}</div>
                <br>
                <br>
                <input type="submit" value="Обновить список" class="submit">
            </form>
        </div>
    </div>

</t:layout>
