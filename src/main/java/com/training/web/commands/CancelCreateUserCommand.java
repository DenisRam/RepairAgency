package com.training.web.commands;

import com.training.web.resourceBundleManager.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CancelCreateUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CancelCreateUserCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!session.getId().equals(session.getAttribute("sessionId"))) {
            LOGGER.info("Session " + session.getId() + " has finished");
            return PageManager.getProperty("path.page.index");
        }

        LOGGER.info("Canceled create user");
        return PageManager.getProperty("path.page.index");
    }
}
