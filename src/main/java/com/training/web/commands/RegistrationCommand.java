package com.training.web.commands;

import com.training.web.resourceBundleManager.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();

        if (session.getAttribute("sessionId") == null) {
            session.setAttribute("sessionId", sessionId);
            LOGGER.info("Session " + session.getId() + " has started");
        }

        return PageManager.getProperty("path.page.registration");
    }
}
