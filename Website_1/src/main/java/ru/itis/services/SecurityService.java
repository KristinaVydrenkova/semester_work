package ru.itis.services;

import ru.itis.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public interface SecurityService {
    public static final String EMAIL_REGEXP = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public static final String AUTH_COOKIE_NAME = "user_uuid";

    UUID signUp(User user, HttpSession session);
    UUID signIn(User user,HttpSession session);
    void validateEmail(String email);
    void validatePassword(String password);
    String encodePassword(String password);
    boolean comparePasswords(String oldPassword, String newPassword);
    User setNewPassword(User user);
    boolean isSigned(HttpServletRequest request);
    void updateDateOfBirth(User user);
    void updateEmail(User user);
    void logOut(String uuid);
    void logOut(HttpServletRequest request, HttpSession session, HttpServletResponse response);
    void signOut(HttpServletRequest request, HttpSession session, HttpServletResponse response);
}
