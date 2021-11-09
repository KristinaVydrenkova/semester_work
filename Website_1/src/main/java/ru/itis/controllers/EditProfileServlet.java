package ru.itis.controllers;

import ru.itis.exceptions.InvalidEmailException;
import ru.itis.exceptions.OccupiedEmailException;
import ru.itis.exceptions.WrongFileException;
import ru.itis.models.User;
import ru.itis.services.PhotoService;
import ru.itis.services.SecurityService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet("/profile/edit")
public class EditProfileServlet extends HttpServlet {
    private PhotoService photoService;
    private SecurityService securityService;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        servletContext = getServletConfig().getServletContext();
        photoService = (PhotoService) servletContext.getAttribute("photoService");
        securityService = (SecurityService) servletContext.getAttribute("securityService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part dateOfBirthPart = request.getPart("dateOfBirth");
        Part emailPart = request.getPart("email");
        Part photoPart = request.getPart("photo");
        User user = (User) request.getSession().getAttribute("user");
        String message = null;
        String dateOfBirth = new BufferedReader(
                new InputStreamReader(dateOfBirthPart.getInputStream()))
                .lines().collect(Collectors.joining());
        String email = new BufferedReader(
                new InputStreamReader(emailPart.getInputStream()))
                .lines().collect(Collectors.joining());
        if(!dateOfBirth.equals("")) {
            user.setDateOfBirth(dateOfBirth);
            securityService.updateDateOfBirth(user);
        }
        if(!email.equals("")){
            try {
                user.setEmail(email);
                securityService.updateEmail(user);
            }catch (InvalidEmailException e){
                message = "Неверный email";
            }catch (OccupiedEmailException e){
                message = "Аккаунт с данным email уже существует";
            }
        }
        if(!photoPart.getSubmittedFileName().equals("")) {
            try{
                photoService.checkContentType(photoPart.getContentType());
                photoService.deleteOldPhoto(user.getPhotoName());
                user.setPhotoName(UUID.randomUUID().toString().replaceAll("-", "") + ".jpg");
                photoService.upload(user, photoPart.getInputStream());
            }catch (WrongFileException e){
                message = "Неправильный формат фото";
            }
        }
        if (message != null) {
            request.setAttribute("message",message);
            getServletContext().getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(request, response);
        }else{
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath() + "/profile");
        }



    }

}
