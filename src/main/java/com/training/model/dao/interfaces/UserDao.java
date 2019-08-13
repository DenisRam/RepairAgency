package com.training.model.dao.interfaces;

import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;

import java.sql.Connection;
import java.util.List;

public interface UserDao {
    int create(String name, String surname, int accountId, String role, Connection connection) throws DataBaseException;
    User read(int id, Connection connection) throws DataBaseException;
    void update(int id, String name, String surname, int accountId, String role, Connection connection) throws DataBaseException;
    void delete(User user, Connection connection) throws DataBaseException;
    List<User> getAll(Connection connection) throws DataBaseException;

}
