package com.training.web.commands;

import com.training.model.entity.*;
import com.training.model.exeptions.DataBaseException;
import com.training.web.resourceBundleManager.MessageManager;
import com.training.web.resourceBundleManager.PageManager;
import com.training.web.services.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SingInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(SingInCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String sessionId = session.getId();

        if (session.getAttribute("sessionId") == null) {
            session.setAttribute("sessionId", sessionId);
            LOGGER.info("Session " + session.getId() + " has started");
        }

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Account account;
        User currentUser;
        LoginService loginService = new LoginService();
        try {
            account = loginService.checkAccount(login,password);
        }catch (DataBaseException e){
            session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
            session.setAttribute("previousPage", "path.page.login");
            LOGGER.error("Cannot get user account from database", e.getCause());
            return PageManager.getProperty("path.page.error");
        }
        if (account != null){
            try {
                currentUser = loginService.getUserbyAccount(account);
            }catch (DataBaseException e) {
                session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
                session.setAttribute("previousPage", "path.page.login");
                LOGGER.error("Can't get user. DB error", e.getCause());
                return PageManager.getProperty("path.page.error");
            }
            LOGGER.info("Account was found");
        } else {
            LOGGER.info("Account was not found");
            request.setAttribute("errorLoginMessage", true);
            return PageManager.getProperty("path.page.login");
        }

        if (currentUser != null && currentUser.getRole().equals("ADMIN")){
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("sessionId", sessionId);

            try {
                ServiceManagementService serviceManagementService = new ServiceManagementService();
                List<Service> servicesList = serviceManagementService.getAllServices();
                session.setAttribute("servicesList", servicesList);
            }catch (Exception e){

//            } catch (NullPointerException e) {
//            session.setAttribute("errorMessage", "message.error.vrongParameters");
//            session.setAttribute("previousPage", "path.page.login");
//            LOGGER.error("Can't load user info", e.getCause());
//            return PageManager.getProperty("path.page.error");
            }
            LOGGER.info("Admin " + currentUser.getId() + " was logged");
            return PageManager.getProperty("path.page.adminPage");
        } else if(currentUser != null && currentUser.getRole().equals("MANAGER")){
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("sessionId", sessionId);
            try {
                ServiceManagementService serviceManagementService = new ServiceManagementService();
                List<Service> servicesList = serviceManagementService.getAllServices();
                session.setAttribute("servicesList", servicesList);
                UserService userService = new UserService();
                List<User> userList = userService.getAllUsers();
                session.setAttribute("userList", userList);
                OrderService orderService = new OrderService();
                List<Order> orderList = orderService.getOrdersByUserIdAndRole(currentUser.getId(), currentUser.getRole());
                session.setAttribute("orderList", orderList);
                return PageManager.getProperty("path.page.managerPage");
            }catch (DataBaseException e){
                session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
                session.setAttribute("previousPage", "path.page.login");
                LOGGER.error("Can't get data. DB error", e.getCause());
            } catch (NullPointerException e) {
            session.setAttribute("errorMessage", "message.error.vrongParameters");
            session.setAttribute("previousPage", "path.page.login");
            LOGGER.error("Can't load user info", e.getCause());
            return PageManager.getProperty("path.page.error");
            }
            LOGGER.info("Manager " + currentUser.getId() + " was logged");
            return PageManager.getProperty("path.page.managerPage");
        } else if(currentUser != null && currentUser.getRole().equals("MASTER")){
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("sessionId", sessionId);
            try {
                ServiceManagementService serviceManagementService = new ServiceManagementService();
                List<Service> servicesList = serviceManagementService.getAllServices();
                session.setAttribute("servicesList", servicesList);
        System.out.println(servicesList);
                UserService userService = new UserService();
                List<User> userList = userService.getAllUsers();
                session.setAttribute("userList", userList);
        System.out.println(userList);
                OrderService orderService = new OrderService();
                List<Order> orderList = orderService.getOrdersByUserIdAndRole(currentUser.getId(), currentUser.getRole());
                session.setAttribute("orderList", orderList);
        System.out.println(orderList);
            }catch (DataBaseException e){
                session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
                session.setAttribute("previousPage", "path.page.login");
                LOGGER.error("Can't get data. DB error", e.getCause());
                return PageManager.getProperty("path.page.error");
            } catch (NullPointerException e) {
            session.setAttribute("errorMessage", "message.error.vrongParameters");
            session.setAttribute("previousPage", "path.page.login");
            LOGGER.error("Can't load manager info", e.getCause());
            return PageManager.getProperty("path.page.error");
            }
            LOGGER.info("Master " + currentUser.getId() + " was logged");
            return PageManager.getProperty("path.page.masterPage");

        } else if(currentUser != null && currentUser.getRole().equals("USER")) {
            session.setAttribute("currentUser", currentUser);
            session.setAttribute("sessionId", sessionId);
            try {
                ServiceManagementService serviceManagementService = new ServiceManagementService();
                List<Service> servicesList = serviceManagementService.getAllServices();
                session.setAttribute("servicesList", servicesList);
                CommentService commentService = new CommentService();
                Map<Integer, Comment> mapUsersComments = commentService.getCommentByUserId(currentUser.getId());
                session.setAttribute("mapUsersComments", mapUsersComments);
                OrderService orderService = new OrderService();
                Map<String, Order> mapUsersOrders = orderService.getUsersOrdersByUserId(currentUser.getId());
                session.setAttribute("mapUsersOrders", mapUsersOrders);
            }catch (DataBaseException e){
                session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
                session.setAttribute("previousPage", "path.page.login");
                LOGGER.error("Can't get data. DB error", e.getCause());
                return PageManager.getProperty("path.page.error");
            } catch (NullPointerException e) {
            session.setAttribute("errorMessage", "message.error.vrongParameters");
            session.setAttribute("previousPage", "path.page.login");
            LOGGER.error("Can't load user info", e.getCause());
            return PageManager.getProperty("path.page.error");
            }
            LOGGER.info("User " + currentUser.getId() + " was logged");
            return PageManager.getProperty("path.page.userPage");
        } else {
            request.setAttribute("errorLoginMessage", true);
            LOGGER.info("Unsuccessful authorization, user was not found. Try again");
            return PageManager.getProperty("path.page.login");
        }
    }
}
