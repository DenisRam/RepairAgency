package com.training.model.dao.interfaces;

import com.training.model.entity.Comment;
import com.training.model.exeptions.DataBaseException;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface CommentDao {

    int create(int orderId, String commetText, int userId, Connection connection) throws DataBaseException;
    Comment read(int id, Connection connection) throws DataBaseException;
    void update(int id, int orderId, String commetText, int userId, Connection connection) throws DataBaseException;
    void delete(Comment entity, Connection connection) throws DataBaseException;
    List<Comment> getAll(Connection connection) throws DataBaseException;

    Map<Integer, Comment> getCommentsByUserId(int userId, Connection connection) throws DataBaseException;
}