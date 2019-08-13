package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.CommentDao;
import com.training.model.entity.Comment;
import com.training.model.exeptions.DataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommentDaoImpl implements CommentDao {

    private static final Logger LOGGER = LogManager.getLogger(CommentDaoImpl.class);

    @Override
    public int create(int orderId, String commetText, int userId, Connection connection) {
        String sql = "INSERT into comments (order_id, comment_text, user_id) VALUES (?, ?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, commetText);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create comment in database", e.getCause());
            throw new DataBaseException("message.error.comment");
        }

        return id;
    }

    @Override
    public Comment read(int id, Connection connection) {
        Comment comment = new Comment();
        String sql = "SELECT * FROM comments WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                comment = new Comment.Builder().
                        setId(resultSet.getInt("id")).
                        setOrderId(resultSet.getInt("order_id")).
                        setCommentText(resultSet.getString("comment_text")).
                        setUserId(id).
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read comment from database", e.getCause());
            throw  new DataBaseException("message.error.comment");
        }
        return comment;
    }

    @Override
    public void update(int id, int orderId, String commetText, int userId, Connection connection) {
        String sql = "UPDATE comments SET order_id=?, comment_text=?, user_id=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, orderId);
            preparedStatement.setString(2, commetText);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update comment in database",e.getCause());
            throw new DataBaseException("message.error.comment");
        }
    }

    @Override
    public void delete(Comment entity, Connection connection) {
        String sql = "DELETE FROM comments WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to delete comment from database",e.getCause());
            throw new DataBaseException("message.error.comment");
        }
    }

    @Override
    public List<Comment> getAll(Connection connection) {
        List<Comment> list = new ArrayList<>();
        String sql = "SELECT * FROM comments ORDER BY id";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                list.add(new Comment.Builder().setId(resultSet.getInt("id")).
                        setOrderId(resultSet.getInt("order_id")).
                        setCommentText(resultSet.getString("comment_text")).
                        setUserId(resultSet.getInt("user_id")).
                        build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read comments from database",e.getCause());
            throw new DataBaseException("message.error.comment");
        }
        return list;
    }

    @Override
    public Map<Integer, Comment> getCommentsByUserId(int userId, Connection connection) throws DataBaseException {
        Map<Integer, Comment> map = new LinkedHashMap<>();
        String sql= "SELECT * FROM comments inner join orders ON comments.order_id = orders.id where comments.user_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                map.put(resultSet.getInt("service_id"),
                        new Comment.Builder().setId(resultSet.getInt("id")).
                        setOrderId(resultSet.getInt("order_id")).
                        setCommentText(resultSet.getString("comment_text")).
                        setUserId(userId).
                        build());
            }
        } catch (SQLException e){
            LOGGER.error("Unable to read comments selected by user id from database",e.getCause());
            throw new DataBaseException("message.error.comment");
        }
        return map;
    }
}
