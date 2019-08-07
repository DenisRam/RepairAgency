package com.training.model.dao;

import com.training.model.dao.implementation.*;
import com.training.model.dao.interfaces.*;

public class DaoFactory {

    public static AccountDao getAccountDao(){
        return new AccountDaoImpl();
    }

    public static CommentDao getCommentDao(){
        return new CommentDaoImpl();
    }

    public static OrderDao getOrderDao(){
        return new OrderDaoImpl();
    }

    public static ServiceDao getServiceDao(){
        return new ServiceDaoImpl();
    }

    public static UserDao getUserDao(){
        return new UserDaoImpl();
    }
}
