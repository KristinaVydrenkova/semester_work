package ru.itis.controllers;

import ru.itis.dto.CheburashkaTestForm;
import ru.itis.dto.EntryTestForm;
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

@WebServlet("/tests/cheburashkaTest")
public class CheburashkaTestServlet extends HttpServlet {
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
        getServletContext().getRequestDispatcher("/WEB-INF/views/cheburashkaTest.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CheburashkaTestForm form = new CheburashkaTestForm();
        form.setQ1(request.getParameter("q1"));
        form.setQ2(request.getParameter("q2"));
        form.setQ3(request.getParameter("q3"));
        form.setQ4(request.getParameter("q4"));
        form.setQ5(request.getParameter("q5"));
        User user = (User) request.getSession().getAttribute("user");
        int scores = testCheckService.checkCheburashkaTest(form,user);
        String result;
        if(scores==-1){
            result = "Вы уже прошли этот тест ранее";
        }else{
            result = String.valueOf(scores);
        }
        response.setContentType("text/html");
        response.getWriter().write(result);
    }
}
