package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.CommentDao;
import com.training.model.entity.Comment;
import com.training.model.exeptions.DBExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

    private static final Logger LOGGER = LogManager.getLogger(CommentDaoImpl.class);

    @Override
    public int create(Comment entity, Connection connection) {
        String sql = "INSERT into comments (order_id, comment_text) VALUES (?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, entity.getOrderId());
            preparedStatement.setString(2, entity.getCommentText());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create comment in database", e.getCause());
            throw new DBExeption("message.error.comment");
        }

        return id;
    }

    @Override
    public Comment read(Integer id, Connection connection) {
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
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read comment from database", e.getCause());
            throw  new DBExeption("message.error.comment");
        }
        return comment;
    }

    @Override
    public void update(Comment entity, Connection connection) {
        String sql = "UPDATE comments SET order_id=?, comment_text=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, entity.getOrderId());
            preparedStatement.setString(2, entity.getCommentText());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update comment in database",e.getCause());
            throw new DBExeption("message.error.comment");
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
            throw new DBExeption("message.error.comment");
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
                        build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read comments from database",e.getCause());
            throw new DBExeption("message.error.comment");
        }
        return list;
    }
}
