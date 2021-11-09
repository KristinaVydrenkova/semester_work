package ru.itis.controllers;

import ru.itis.models.Point;
import ru.itis.models.User;
import ru.itis.services.TestCheckService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile/myPoints")
public class MyPointsServlet extends HttpServlet {
    private TestCheckService testCheckService;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = getServletConfig().getServletContext();
        testCheckService = (TestCheckService) servletContext.getAttribute("testCheckService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        List<Point> points = testCheckService.getAllResults(user.getId());
        request.setAttribute("points",points);
        getServletContext().getRequestDispatcher("/WEB-INF/views/myPoints.jsp").forward(request,response);
    }
}
