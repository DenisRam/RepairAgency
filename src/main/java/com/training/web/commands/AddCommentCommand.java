package com.training.web.commands;

import com.training.model.entity.Comment;
import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;
import com.training.web.resourceBundleManager.MessageManager;
import com.training.web.resourceBundleManager.PageManager;
import com.training.web.services.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AddCommentCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger(AddCommentCommand.class);
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (!session.getId().equals(session.getAttribute("sessionId"))) {
            LOGGER.info("Session " + session.getId() + " has finished");
            return PageManager.getProperty("path.page.login");
        }

        int currentOrderId = Integer.valueOf(request.getParameter("currentOrderId"));
        String commentText = (String) request.getParameter("commentText");
        User currentUser = (User) session.getAttribute("currentUser");

        try{
            CommentService commentService = new CommentService();
            int newComment = commentService.createComment(currentOrderId,commentText,currentUser.getId());
            Map<Integer, Comment> mapUsersComments = commentService.getCommentByUserId(currentUser.getId());
            session.setAttribute("mapUsersComments", mapUsersComments);
        }catch (DataBaseException e){
            session.setAttribute("errorMessage", MessageManager.getProperty(e.getMessage()));
            session.setAttribute("previousPage", "path.page.login");
            LOGGER.error("New comment was not created. Database error", e.getCause());
            return PageManager.getProperty("path.page.error");
        } catch (NullPointerException e) {
            session.setAttribute("errorMessage", "message.error.vrongParameters");
            session.setAttribute("previousPage", "path.page.login");
            LOGGER.error("Can't load user info", e.getCause());
            return PageManager.getProperty("path.page.error");
        }
        LOGGER.info("User " + currentUser.getName() + " " + currentUser.getSurname() + "create comment");
        return PageManager.getProperty("path.page.userPage");
    }
}
