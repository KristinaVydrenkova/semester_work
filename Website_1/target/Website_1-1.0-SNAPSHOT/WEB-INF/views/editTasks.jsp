<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Редактировать">
    <%@include file="header.jsp" %>
    <div class="page">
        <div class="information">
            <div class="title_tasks">
                Редактировать задания
                <div class="line_tasks"></div>
            </div>
            <div><a class="back_link" href="<c:url value="/profile"/>">Назад</a></div>
            <form action="edit" method="post" class="students_form">
                <h1>Удалить задание</h1>
                <label class="edit_label" for="deleteTasks">Выберите задания,которые хотите убрать из списка</label><br>
                <select multiple name="deleteTasks" id="deleteTasks">
                    <c:forEach items="${tasks}" var="task">
                        <option label="задание" value="${task.id}">${task.id} ${task.task}</option>
                    </c:forEach>
                </select>
                <br>
                <br>
                <h1>Добавить задание</h1>
                <label class="edit_label" for="addTask">Введите текст задания, которое хотите добавить</label><br>
                <input id="addTask" name="addTask" class="input_students">
                <br>
                <br>
                <label class="edit_label" for="deadline">Введите дату сдачи</label><br>
                <input id="deadline" name="deadline" class="input_delete_id">
                <br>
                <div class="message">${message}</div>
                <br>
                <input type="submit" value="Изменить" class="submit">
            </form>
        </div>
    </div>

</t:layout>
