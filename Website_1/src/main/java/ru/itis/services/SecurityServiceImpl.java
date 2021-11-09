package ru.itis.services;

import org.apache.commons.codec.digest.DigestUtils;
import ru.itis.database.UserRepository;
import ru.itis.exceptions.*;
import ru.itis.models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityServiceImpl implements SecurityService {
    private UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UUID signUp(User user, HttpSession session) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        user.setPassword(encodePassword(user.getPassword()));
        UUID uuid = UUID.randomUUID();
        userRepository.addUser(user);
        user.setId(userRepository.getUserId(user.getEmail()));
        userRepository.addUuid(user,uuid.toString());
        session.setAttribute("user",user);
        return uuid;
    }

    @Override
    public void validateEmail(String email){
        Matcher m = Pattern.compile(EMAIL_REGEXP).matcher(email);
        if(!m.matches()){
            throw new InvalidEmailException("wrong email!");
        }
        if(userRepository.findEmail(email)){
            throw new OccupiedEmailException(email + " is occupied");
        }
    }

    @Override
    public void validatePassword(String password){
        if(password.length()<8){
            throw new BadPasswordException("Password is too short");
        }
    }
    @Override
    public String encodePassword(String password){
        String hash = DigestUtils.md5Hex(password);
        return hash;
    }
    @Override
    public UUID signIn(User user,HttpSession session){
        if(!userRepository.findEmail(user.getEmail())) {
            throw new NoSuchEmailException(user.getEmail() + " doesn't exist");
        }
        if(encodePassword(user.getPassword()).equals(userRepository.findPasswordByEmail(user.getEmail()))){
            UUID uuid = UUID.randomUUID();
            user.setId(userRepository.getUserId(user.getEmail()));
            user = userRepository.getUserById(user.getId());
            userRepository.addUuid(user,uuid.toString());
            session.setAttribute("user", user);
            return uuid;
        }else{
            throw new WrongPasswordException();
        }
    }
    @Override
    public boolean isSigned(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null){
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie c : cookies){
                if(c.getName().equals(AUTH_COOKIE_NAME)){
                    User user = userRepository.getUserByUUID(c.getValue());
                    if(user != null){
                        session.setAttribute("user", user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void updateDateOfBirth(User user) {
        userRepository.updateDateOfBirth(user);
    }

    @Override
    public void updateEmail(User user) {
        User user2 = userRepository.getUserById(user.getId());
        if(!user2.getEmail().equals(user.getEmail())) {
            validateEmail(user.getEmail());
            userRepository.updateEmail(user);
        }
    }
    @Override
    public void logOut(HttpServletRequest req, HttpSession session, HttpServletResponse response) {
        Cookie[] cookies = req.getCookies();
        for(Cookie c : cookies){
            if(c.getName().equals(SecurityService.AUTH_COOKIE_NAME)){
                logOut(c.getValue());
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        session.invalidate();
    }
    @Override
    public void logOut(String uuid) {
        userRepository.removeUUID(uuid);
    }

    @Override
    public void signOut(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies){
            if(c.getName().equals(SecurityService.AUTH_COOKIE_NAME)){
                logOut(c.getValue());
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        User user = (User) request.getSession().getAttribute("user");
        userRepository.deleteAllUUIDs(user.getId());
        userRepository.deleteUser(user.getId());
        session.invalidate();
    }

    @Override
    public boolean comparePasswords(String oldPassword, String inputPassword) {
        return oldPassword.equals(encodePassword(inputPassword));
    }

    @Override
    public User setNewPassword(User user) {
        validatePassword(user.getPassword());
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.updatePassword(user);
        return user;
    }
}
