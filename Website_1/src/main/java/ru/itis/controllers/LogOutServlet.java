package ru.itis.controllers;

import ru.itis.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logOut")
public class LogOutServlet extends HttpServlet {
    private ServletContext servletContext;
    private SecurityService securityService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = getServletConfig().getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        securityService.logOut(req,req.getSession(),resp);
        resp.sendRedirect(req.getContextPath());
    }
}
