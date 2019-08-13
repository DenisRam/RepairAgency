package com.training.web.commands;

import com.training.web.resourceBundleManager.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangeLocaleCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger(ChangeLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("sessionId") != null && !session.getId().equals(session.getAttribute("sessionId"))){
            LOGGER.info("Session " + session.getId() + " was finished");
            return PageManager.getProperty("path.page.index");
        }
        String currentPage = request.getParameter("currentPage");
        String locale = request.getParameter("locale");
        session.setAttribute("locale", locale);
        if (locale.equals("1")){
            LOGGER.info("Locale was changed to EN");
        } else {
            LOGGER.info("Locale was changed to UA");
        }
        return PageManager.getProperty(currentPage);

    }
}
