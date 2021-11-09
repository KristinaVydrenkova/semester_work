package ru.itis.controllers;

import ru.itis.exceptions.NoSuchEmailException;
import ru.itis.models.Student;
import ru.itis.models.User;
import ru.itis.services.StudentsService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/profile/myStudents/edit")
public class EditStudentsServlet extends HttpServlet {
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
        List<Student> students = studentsService.getStudents(user.getId());
        request.setAttribute("students",students);
        getServletContext().getRequestDispatcher("/WEB-INF/views/editStudents.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        String message = null;

        String addStudentsString = request.getParameter("addStudents");
        String [] deleteStudentsArray = request.getParameterValues("deleteStudents");

        if(!addStudentsString.equals("")){
            List<String> addStudents = Arrays.asList(addStudentsString.split(" "));
            try {
                studentsService.addStudents(addStudents, user);
            }catch (NoSuchEmailException e){
                message = "Пользователя с email " + e.getMessage() + " не существует";
            }
        }
        if(deleteStudentsArray!=null){
            List<String> deleteStudents = Arrays.asList(deleteStudentsArray);
            try{
                studentsService.deleteStudents(deleteStudents,user);
            }catch (NoSuchEmailException e){
                message = "Пользователя с email " + e.getMessage() + " не существует";
            }
        }
        if (message != null) {
            request.setAttribute("message",message);
            getServletContext().getRequestDispatcher("/WEB-INF/views/editStudents.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/profile/myStudents");
        }
    }
}
