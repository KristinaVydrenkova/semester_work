package ru.itis.controllers;

import ru.itis.models.User;
import ru.itis.services.PhotoService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private PhotoService photoService;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = config.getServletContext();
        photoService = (PhotoService)servletContext.getAttribute("photoService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("auth")!=null&&request.getSession().getAttribute("auth").equals(true)){
            User user = (User) request.getSession().getAttribute("user");
            photoService.setPhotoName(user);
            getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath()+"/signIn");
        }

    }

}
