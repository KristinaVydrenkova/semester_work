package ru.itis.controllers;

import ru.itis.exceptions.NoSuchEmailException;
import ru.itis.exceptions.NoSuchTaskIdException;
import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.services.StudentsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/profile/teacherTasks/edit")
public class EditTasksServlet extends HttpServlet {
    private StudentsService studentsService;
    private ServletContext servletContext;

    @Override
    public void init() throws ServletException {
        super.init();
        servletContext = getServletConfig().getServletContext();
        studentsService = (StudentsService) servletContext.getAttribute("studentsService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Task> tasks = studentsService.getTasks(user.getId());
        request.setAttribute("tasks",tasks);
        getServletContext().getRequestDispatcher("/WEB-INF/views/editTasks.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String message = null;
        String addTask = request.getParameter("addTask");
        String deadline = request.getParameter("deadline");
        String[] deleteTasksArray = request.getParameterValues("deleteTasks");

        if(deleteTasksArray!=null){
            List<String> deleteTask = Arrays.asList(deleteTasksArray);
            studentsService.deleteTask(deleteTask);
        }
        if(!addTask.equals("")){
            Task task = new Task();
            task.setTeacherId(user.getId());
            task.setTeacherName(user.getLastName() + " " + user.getFirstName() + " " + user.getPatronymic());
            task.setTask(addTask);
            task.setDeadline(deadline);
            studentsService.addTask(task);
        }
        if (message != null) {
            request.setAttribute("message",message);
            getServletContext().getRequestDispatcher("/WEB-INF/views/editTasks.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/profile/teacherTasks");
        }
    }
}
