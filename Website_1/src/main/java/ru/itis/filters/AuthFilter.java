package ru.itis.filters;

import ru.itis.services.SecurityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    protected final String[] protectedPaths = {"/profile","/tests"};
    private SecurityService securityService;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
        securityService = (SecurityService)context.getAttribute("securityService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        if(securityService.isSigned(request)){
            request.getSession().setAttribute("auth",true);
        }else{
            request.getSession().setAttribute("auth", false);
            boolean isProtected = false;
            for(String s : protectedPaths){
                if(s.equals(request.getRequestURI().substring(request.getContextPath().length()))){
                    isProtected = true;
                }
            }
            if(isProtected){
                response.sendRedirect(request.getContextPath() + "/signIn");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
