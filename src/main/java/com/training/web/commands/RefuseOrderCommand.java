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
import java.util.List;

public class RefuseOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RefuseOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!session.getId().equals(session.getAttribute("sessionId"))) {
            LOGGER.info("Session " + session.getId() + " has finished");
            return PageManager.getProperty("path.page.login");
        }

        int currentOrderId = Integer.valueOf(request.getParameter("currentOrder"));
        User currentUser = (User) session.getAttribute("currentUser");
        String refuseReason = request.getParameter("refuseReason");
        System.out.println(refuseReason);

        try {
            OrderService orderService = new OrderService();
            orderService.confirmOrder(currentOrderId, currentUser.getId(),"REFUSE", refuseReason);
            List<Order> orderList = orderService.getOrdersByUserIdAndRole(currentUser.getId(),currentUser.getRole());
            session.setAttribute("orderList", orderList);
        }catch (DataBaseException e) {
            session.setAttribute( "errorMessage", MessageManager.getProperty(e.getMessage()));
            session.setAttribute("previousPage", "path.page.managerPage");
            LOGGER.error("Publication was not created. DB error", e.getCause());
            return PageManager.getProperty("path.page.error");
        }
        LOGGER.info("Order " + currentOrderId + " was refused");
        return PageManager.getProperty("path.page.managerPage");
    }

}
