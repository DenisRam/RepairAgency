package com.training.model.dao.interfaces;

import com.training.model.entity.Service;
import com.training.model.exeptions.DataBaseException;

import java.sql.Connection;
import java.util.List;

public interface ServiceDao {
    int create(String serviceName, double price, Connection connection) throws DataBaseException;
    Service read(int id, Connection connection) throws DataBaseException;
    void update(int id, String serviceName, double price, Connection connection) throws DataBaseException;
    void delete(Service entity, Connection connection) throws DataBaseException;
    List<Service> getAll(Connection connection) throws DataBaseException;
}
