package com.ttn.reap.component;

import com.ttn.reap.DTO.LoggedInUserDetails;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ReapInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Avoid a redirect loop for some urls
        HttpSession session = request.getSession(false);

        if(request.getRequestURI().contains("/api"))
            return  true;

        if (!request.getRequestURI().equals("/reap/login")
                && !request.getRequestURI().equals("/reap/register")
                &&!request.getRequestURI().endsWith(".css")
                &&!request.getRequestURI().endsWith(".png")
                &&!request.getRequestURI().endsWith(".jpg")
                &&!request.getRequestURI().endsWith(".jpeg")
                && !request.getRequestURI().equals("/reap/forgotpwd")
                && !request.getRequestURI().equals("/reap/reset")) {
            LoggedInUserDetails employee = (LoggedInUserDetails) request.getSession().getAttribute("loggedInUser");
            if (employee == null) {
                response.sendRedirect("/reap/login");
                return false;
            }
            else if(request.getRequestURI().equals("/reap/logout")){
                request.getSession().invalidate();
                response.sendRedirect("/reap/login");
                return false;
            }
        }
        return true;
    }

//    @Override
//    public void postHandle(
//            HttpServletRequest request, HttpServletResponse response, Object handler,
//            ModelAndView modelAndView)
//            throws Exception {
//        System.out.println("postHandle " + request.getRequestURI());
//    }
//    @Override
//    public void afterCompletion(
//            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//            throws Exception {
//        System.out.println("afterCompletion " + request.getRequestURI());
//    }


}
