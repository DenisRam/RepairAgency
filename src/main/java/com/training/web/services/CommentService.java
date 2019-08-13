package com.training.web.services;

import com.training.model.dao.DaoFactory;
import com.training.model.dao.interfaces.CommentDao;
import com.training.model.entity.Comment;
import com.training.model.exeptions.DataBaseException;
import com.training.model.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Map;

public class CommentService {
    private static final Logger LOGGER = LogManager.getLogger(CommentService.class);

    CommentDao commentDao = DaoFactory.getCommentDao();

    /**
     * Get all user's comments with service id
     * @param userId user's id in database
     * @return Map with key services id an value user's comment
     * @exception DataBaseException error in Dao layer
     */

    public Map<Integer, Comment> getCommentByUserId(int userId){
        Connection connection = ConnectionPool.getConnection(true);
        Map<Integer, Comment> map = null;
        try {
            map = commentDao.getCommentsByUserId(userId, connection);
        }catch (DataBaseException e){
            LOGGER.error("Can't get users's comments", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return map;
    }

    /**
     * Create comment in datbase
     * @param orderId order's id
     * @param commentText comment's text
     * @param userId user's id
     * @return comment id or 0
     */
    public int createComment(int orderId, String commentText, int userId){
        Connection connection = ConnectionPool.getConnection(false);
        int commentId;
        try{
            commentId = commentDao.create(orderId,commentText,userId,connection);

            ConnectionPool.commitTransaction(connection);
        }catch (DataBaseException e){
            ConnectionPool.transactionRollback(connection);
            LOGGER.error("Can't create comment", e.getCause());
            throw e;
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return commentId;
    }
}
