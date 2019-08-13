package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.UserDao;
import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public int create(String name, String surname, int accountId, String role, Connection connection) {
        String sql = "INSERT into users (name, surname, account_id, role) VALUES (?, ?, ?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, accountId);
            preparedStatement.setString(4,role);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create user in database", e.getCause());
            throw new DataBaseException("message.error.user");
        }
        return id;
    }

    @Override
    public User read(int id, Connection connection) {
        User user = new User();
        String sql = "SELECT * FROM users WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User.Builder().
                        setId(resultSet.getInt("id")).
                        setName(resultSet.getString("name")).
                        setSurname(resultSet.getString("surname")).
                        setAccountId(resultSet.getInt("account_id")).
                        setRole(resultSet.getString("role")).
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read user in database", e.getCause());
            throw new DataBaseException("message.error.user");
        }
        return user;
    }

    @Override
    public void update(int id, String name, String surname, int accountId, String role, Connection connection) {
        String sql = "UPDATE users SET name=?, surname=?, account_id=?, role=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, accountId);
            preparedStatement.setString(4, role);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update user in database", e.getCause());
            throw new DataBaseException("message.error.user");
        }
    }

    @Override
    public void delete(User user, Connection connection) {
        String sql = "DELETE FROM users WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,user.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to delete user from database",e.getCause());
            throw new DataBaseException("message.error.user");
        }
    }

    @Override
    public List<User> getAll(Connection connection) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                list.add(new User.Builder().
                        setId(resultSet.getInt("id")).
                        setName(resultSet.getString("name")).
                        setSurname(resultSet.getString("surname")).
                        setAccountId(resultSet.getInt("account_id")).
                        setRole(resultSet.getString("role")).
                        build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read users from database",e.getCause());
            throw new DataBaseException("message.error.user");
        }
        return list;
    }
}
