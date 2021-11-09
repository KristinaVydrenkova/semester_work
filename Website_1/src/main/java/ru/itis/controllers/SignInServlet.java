package ru.itis.controllers;

import ru.itis.exceptions.NoSuchEmailException;
import ru.itis.exceptions.WrongPasswordException;
import ru.itis.models.User;
import ru.itis.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.UUID;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    private SecurityService securityService;
    private ServletContext servletContext;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = getServletConfig().getServletContext();
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = null;
        User user = new User();
        if(request.getParameter("email")!=null && request.getParameter("password")!=null) {
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            try{
                UUID uuid = securityService.signIn(user,request.getSession());
                Cookie c = new Cookie(SecurityService.AUTH_COOKIE_NAME, uuid.toString());
                c.setMaxAge(60*60*24*365);
                response.addCookie(c);
            }catch (WrongPasswordException e){
                message = "Пароль неверный";
            }catch (NoSuchEmailException e){
                message = "Такого пользователя не существует";
            }
        }else{
            message = "Заполните все поля";
        }
        if (message != null) {
            request.setAttribute("message",message);
            getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/profile");
        }

    }
}
