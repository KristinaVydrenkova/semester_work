package ru.itis.controllers;

import ru.itis.exceptions.BadPasswordException;
import ru.itis.exceptions.InvalidEmailException;
import ru.itis.exceptions.OccupiedEmailException;
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
import java.util.UUID;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
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
        getServletContext().getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = null;
        if(request.getParameter("password").equals(request.getParameter("password-repeat"))){
            User user = new User();
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setPatronymic(request.getParameter("patronymic"));
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            user.setRole(Integer.parseInt(request.getParameter("role")));
            try {
                UUID uuid = securityService.signUp(user, request.getSession());
                Cookie c = new Cookie(SecurityService.AUTH_COOKIE_NAME, uuid.toString());
                c.setMaxAge(60*60*24*365);
                response.addCookie(c);
            }catch (InvalidEmailException e){
                message = "Неверный email";
            }catch (OccupiedEmailException e){
                message = "Аккаунт с данным email уже существует";
            }catch (BadPasswordException e){
                message = "Пароль должен содержать больше 8 символов";
            }
        }else{
            message = "Пароли не совпадают";
        }
        if (message != null) {
            request.setAttribute("message",message);
            getServletContext().getRequestDispatcher("/WEB-INF/views/signup.jsp").forward(request, response);
        }else{
            response.sendRedirect(request.getContextPath() + "/profile");
        }

    }

}
