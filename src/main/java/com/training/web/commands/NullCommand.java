package com.training.web.commands;

import com.training.web.resourceBundleManager.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NullCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(NullCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("previousPage", "currentPage");
        LOGGER.error("No command action");
        return PageManager.getProperty("path.page.error");

    }
}
