package com.training.web.services;

import com.training.model.dao.DaoFactory;
import com.training.model.dao.interfaces.AccountDao;
import com.training.model.dao.interfaces.UserDao;
import com.training.model.entity.Account;
import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;
import com.training.model.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class LoginService {

    private static final Logger LOGGER = LogManager.getLogger(LoginService.class);

    private AccountDao accountDao = DaoFactory.getAccountDao();
    private UserDao userDao = DaoFactory.getUserDao();

    /**
     * Check login
     * @param login user login
     * @return true if the login exists in the database
     * @exception DataBaseException errors in DAO layer
     */

    public boolean checkLogin(String login){
        Connection connection = ConnectionPool.getConnection(true);
        try {
            List<Account> accountList = accountDao.getAll(connection);
            for (Account account : accountList){
                if (login.equals(account.getLogin())){
                    return true;
                }
            }
        }catch (DataBaseException e){
            LOGGER.error("Cannot check login", e.getCause());
            throw e;
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return false;
    }

    /**
     * Check account in database
     * @param login user's login
     * @param password user's password
     * @return account if the search is successful or null if not
     * @exception DataBaseException error in Dao layer
     */
    public Account checkAccount(String login, String password){
        Connection connection = ConnectionPool.getConnection(true);
        try {
            List<Account> accountList = accountDao.getAll(connection);
            for (Account account : accountList){
                if(login.equals(account.getLogin()) && password.equals(account.getPassword())){
                    return account;
                }
            }
        } catch (DataBaseException e){
            LOGGER.error("Cannot check account", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return null;
    }

    /**
     * Create login and user
     * @param login user's login
     * @param password user's password
     * @param name user's name
     * @param surname user's suname
     * @return user's id in database
     * @exception DataBaseException error in Dao
     */
    public int createLoginAndUser(String login, String password, String name, String surname){
        Connection connection = ConnectionPool.getConnection(false);
        int userId;
        try {
            int accountId = accountDao.create(login, password, connection);
            userId = userDao.create(name,surname,accountId,"USER",connection);

            ConnectionPool.commitTransaction(connection);
        } catch (DataBaseException e){
            ConnectionPool.transactionRollback(connection);
            LOGGER.error("Cannot create user", e.getCause());
            throw e;
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return userId;
    }

    /**
     * Get user from database
     * @param id user's id
     * @return user from database or null
     * @throws DataBaseException error in Dao
     */
    public User getUserbyId(int id){
        Connection connection = ConnectionPool.getConnection(true);
        User user = null;
        try {
            user = userDao.read(id,connection);
        }catch (DataBaseException e){
            LOGGER.error("Cannot get user", e.getCause());
            throw e;
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return user;
    }

    /**
     * Get user from database
     * @param account user's account
     * @return user if the search is successful or null if not
     * @exception DataBaseException error is database layer
     */
    public User getUserbyAccount(Account account){
        Connection connection = ConnectionPool.getConnection(true);
        try {
            List<User> users = userDao.getAll(connection);
            for (User user : users){
                if(account.getId() == user.getAccountId()){
                    return user;
                }
            }
        }catch (DataBaseException e){
            LOGGER.error("Cannot get user", e.getCause());
            throw e;
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return null;
    }

}
