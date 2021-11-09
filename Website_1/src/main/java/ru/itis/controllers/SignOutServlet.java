package ru.itis.controllers;

import ru.itis.models.User;
import ru.itis.services.PhotoService;
import ru.itis.services.SecurityService;
import ru.itis.services.StudentsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signOut")
public class SignOutServlet extends HttpServlet {
    private ServletContext servletContext;
    private SecurityService securityService;
    private StudentsService studentsService;
    private PhotoService photoService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = getServletConfig().getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
        studentsService = (StudentsService) servletContext.getAttribute("studentsService");
        photoService = (PhotoService) servletContext.getAttribute("photoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        photoService.deleteOldPhoto(user.getPhotoName());
        studentsService.signOut(user);
        securityService.signOut(req,req.getSession(),resp);
        resp.sendRedirect(req.getContextPath());
    }
}
