package com.training.web.commands;

import com.training.model.entity.Service;
import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;
import com.training.web.resourceBundleManager.MessageManager;
import com.training.web.resourceBundleManager.PageManager;
import com.training.web.services.LoginService;
import com.training.web.services.ServiceManagementService;
import com.training.web.validation.LoginValidator;
import com.training.web.validation.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CreateUserCommand implements Command{

    private static final Logger LOGGER = LogManager.getLogger(CreateUserCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);

        if (session.getAttribute("sessionId") == null) {
            session.setAttribute("sessionId", session.getId());
            LOGGER.info("Session " + session.getId() + " has started");
        }

        String userName = request.getParameter("userName");
        String userSurname = request.getParameter("userSurname");
        String login = request.getParameter("userLogin");
        String password = request.getParameter("userPassword");
        LoginService loginService = new LoginService();

        try {
            if(loginService.checkLogin(login)){
                request.setAttribute("dublicateAccount", true);
                LOGGER.info("Dublicate account");
                return PageManager.getProperty("path.page.registration");
            } else {
                Map<String, Boolean> mapLoginValidate = LoginValidator.validate(login,password);
                Map<String, Boolean> mapUserValidate = UserValidator.validate(userName, userSurname);
                if (mapLoginValidate.isEmpty() & mapUserValidate.isEmpty()){
                    int userId = loginService.createLoginAndUser(login,password,userName,userSurname);
                    User currentUser = loginService.getUserbyId(userId);
                    LOGGER.info("User " + userName + " " + " " + userSurname + " was created");
                    session.setAttribute("currentUser", currentUser);
                    session.setAttribute("currentUserRole", currentUser.getRole());
                    session.setAttribute("currentUserId", currentUser.getId());
                    ServiceManagementService serviceManagementService = new ServiceManagementService();
                    List<Service> servicesList = serviceManagementService.getAllServices();
                    session.setAttribute("servicesList", servicesList);
                return PageManager.getProperty("path.page.userPage");
                } else {
                    for (Map.Entry<String, Boolean> entry : mapLoginValidate.entrySet()) {
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                    for (Map.Entry<String, Boolean> entry : mapUserValidate.entrySet()){
                        request.setAttribute(entry.getKey(), entry.getValue());
                    }
                    LOGGER.info("Can't create user");
                    return PageManager.getProperty("path.page.registration");
                }
            }
        }catch (DataBaseException e){
            session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
            session.setAttribute("previousPage", PageManager.getProperty("path.page.registration"));
            LOGGER.error("Cannot check login. Database error", e.getCause());
            return PageManager.getProperty("path.page.error");
        } catch (NullPointerException e){
            request.setAttribute("errorMessage", "message.error.vrongParameters");
            request.setAttribute("previousPage", PageManager.getProperty("path.page.registration"));
            LOGGER.error("Cannot create user", e.getCause());
            return PageManager.getProperty("path.page.error");
        }
    }

}
