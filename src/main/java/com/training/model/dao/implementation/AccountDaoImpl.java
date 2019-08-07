package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.AccountDao;
import com.training.model.entity.Account;
import com.training.model.exeptions.DBExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private static final Logger LOGGER = LogManager.getLogger(AccountDaoImpl.class);


    @Override
    public int create(Account entity, Connection connection) {
        String sql = "INSERT into accounts (login, password) VALUES (?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create account in database", e.getCause());
            throw new DBExeption("message.error.account");
        }

        return id;
    }

    @Override
    public Account read(Integer id, Connection connection) {
        Account account = new Account();
        String sql = "SELECT * FROM accounts WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account = new Account.Builder().
                        setId(resultSet.getInt("id")).
                        setLogin(resultSet.getString("login")).
                        setPassword(resultSet.getString("password")).
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read account from database", e.getCause());
            throw  new DBExeption("message.error.account");
        }
        return account;
    }

    @Override
    public void update(Account entity, Connection connection) {
        String sql = "UPDATE accounts SET login=?, password=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update account in database",e.getCause());
            throw new DBExeption("message.error.account");
        }

    }

    @Override
    public void delete(Account entity, Connection connection) {
        String sql = "DELETE FROM accounts WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to delete account from database",e.getCause());
            throw new DBExeption("message.error.account");
        }
    }

    @Override
    public List<Account> getAll(Connection connection) {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY id";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                list.add(new Account.Builder()
                        .setId(resultSet.getInt("id"))
                        .setLogin(resultSet.getString("login"))
                        .setPassword(resultSet.getString("password"))
                        .build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read accounts from database",e.getCause());
            throw new DBExeption("message.error.account");
        }
        return list;
    }
}
