package ru.itis.controllers;

import ru.itis.models.Task;
import ru.itis.models.User;
import ru.itis.services.StudentsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile/studentTasks")
public class StudentTasksServlet extends HttpServlet {
    private ServletContext servletContext;
    private StudentsService studentsService;

    @Override
    public void init() throws ServletException {
        super.init();
        servletContext = getServletConfig().getServletContext();
        studentsService = (StudentsService) servletContext.getAttribute("studentsService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<Task> tasks = studentsService.getStudentTasks(user.getId());
        request.setAttribute("tasks",tasks);
        getServletContext().getRequestDispatcher("/WEB-INF/views/studentTasks.jsp").forward(request,response);
    }
}
