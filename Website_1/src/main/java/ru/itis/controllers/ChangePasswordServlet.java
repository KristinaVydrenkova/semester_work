package ru.itis.controllers;

import ru.itis.exceptions.*;
import ru.itis.models.User;
import ru.itis.services.SecurityService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile/changePassword")
public class ChangePasswordServlet extends HttpServlet {
    private SecurityService securityService;
    private ServletContext servletContext;

    @Override
    public void init() throws ServletException {
        super.init();
        servletContext = getServletConfig().getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/changePassword.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = null;
        User user = (User)request.getSession().getAttribute("user");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatPassword = request.getParameter("repeatPassword");
        if(oldPassword!=null&&newPassword!=null&&repeatPassword!=null) {
            if (newPassword.equals(repeatPassword)) {
                if (securityService.comparePasswords(user.getPassword(), oldPassword)) {
                    user.setPassword(newPassword);
                    try {
                        user = securityService.setNewPassword(user);
                        request.getSession().setAttribute("user", user);
                    } catch (BadPasswordException e) {
                        message = "Пароль должен содержать больше 8 символов";
                    }
                } else {
                    message = "Неверный пароль";
                }
            }else{
                message = "Пароли не совпадают";
            }
        } else {
            message = "Заполните все поля";
        }
        if (message != null) {
            request.setAttribute("message", message);
            response.setContentType("text/html");
            response.getWriter().write(message);
        } else {
            response.setContentType("text/html");
            response.getWriter().write("Пароль успешно изменен");
        }
    }
}
