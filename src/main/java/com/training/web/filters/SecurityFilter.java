package com.training.web.filters;


import com.training.model.entity.User;
import com.training.web.resourceBundleManager.PageManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter(filterName = "security")
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");

        if(currentUser == null){
            resp.sendRedirect(PageManager.getProperty("path.page.login"));
        }
    }

    @Override
    public void destroy() {

    }
}
