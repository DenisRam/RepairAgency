package com.training.web.commands;

import com.training.model.entity.Order;
import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;
import com.training.web.resourceBundleManager.MessageManager;
import com.training.web.resourceBundleManager.PageManager;
import com.training.web.services.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CreateOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CreateOrderCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!session.getId().equals(session.getAttribute("sessionId"))) {
            LOGGER.info("Session " + session.getId() + " has finished");
            return PageManager.getProperty("path.page.login");
        }

        int currentServiceId = Integer.valueOf(request.getParameter("currentServiceId"));
        User currentUser = (User) session.getAttribute("currentUser");
        int newOrderServiceId;
        try {
            OrderService orderService = new OrderService();
            newOrderServiceId = orderService.createNewOrder(currentUser.getId(),currentServiceId,"NEW");
            Map<String, Order> mapUsersOrders = orderService.getUsersOrdersByUserId(currentUser.getId());
            session.setAttribute("mapUsersOrders", mapUsersOrders);
        }catch (DataBaseException e){
            session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
            session.setAttribute("previousPage", "path.page.userPage");
            LOGGER.error("New order was not created. Database error", e.getCause());
            return PageManager.getProperty("path.page.error");
        } catch (NullPointerException e) {
            session.setAttribute("errorMessage", MessageManager.getProperty("message.error.vrongParameters"));
            session.setAttribute("previousPage", "path.page.userPage");
            LOGGER.error("Can't create new order", e.getCause());
            return PageManager.getProperty("path.page.error");
        }
        LOGGER.info("User " + currentUser.getName() + " " + currentUser.getSurname() + "create order, with id = " + newOrderServiceId);
        return PageManager.getProperty("path.page.userPage");
    }
}
