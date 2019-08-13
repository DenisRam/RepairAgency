package com.training.model.dao.interfaces;


import com.training.model.entity.Account;
import com.training.model.exeptions.DataBaseException;

import java.sql.Connection;
import java.util.List;

public interface AccountDao {

    int create(String login, String password, Connection connection) throws DataBaseException;
    Account read(int id, Connection connection)throws DataBaseException;
    void update(int id, String login, String password, Connection connection)throws DataBaseException;
    void delete(Account entity, Connection connection)throws DataBaseException;
    List<Account> getAll(Connection connection)throws DataBaseException;
}
