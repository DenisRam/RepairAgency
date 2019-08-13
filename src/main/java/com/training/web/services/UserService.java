package com.training.web.services;

import com.training.model.dao.DaoFactory;
import com.training.model.dao.interfaces.UserDao;
import com.training.model.entity.User;
import com.training.model.exeptions.DataBaseException;
import com.training.model.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    UserDao userDao = DaoFactory.getUserDao();

    /**
     * Get all users from database
     * @return list with all users from database or null
     * @throws DataBaseException errors in DAO layer
     */
    public List<User> getAllUsers(){
        Connection connection = ConnectionPool.getConnection(true);
        List<User> userList = null;
        try {
            userList = userDao.getAll(connection);
        }catch (DataBaseException e){
            LOGGER.error("Can't gel all users from database", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return userList;
    }



}
