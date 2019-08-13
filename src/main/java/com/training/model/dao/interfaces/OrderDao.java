package com.training.model.dao.interfaces;

import com.training.model.entity.Order;
import com.training.model.exeptions.DataBaseException;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    int create(int userId, int serviceId, String status, int managerId, Date reviewDate, String rejectionReason,
               int masterId, Date repairStartTime, Date repairFinish, Connection connection) throws DataBaseException;
    int create(Order order, Connection connection) throws DataBaseException;
    Order read(int id, Connection connection) throws DataBaseException;
    void update(Order order, Connection connection) throws DataBaseException;
    void delete(Order entity, Connection connection) throws DataBaseException;
    List<Order> getAll(Connection connection) throws DataBaseException;


    void updateOrderToRefuseOrComfir(int id, int managerId, String status, Date reviewDate, String rejectionReason, Connection connection)throws DataBaseException;
    void updateOrderToRepaiStart(int id, int masterId, String status, Date repairStart, Connection connection) throws DataBaseException;
    void updateOrderToRepairFinish(int id, String status, Date repairFinish, Connection connection) throws DataBaseException;
    Map<String, Order> getOrdersByUserId(int userId, Connection connection)throws DataBaseException;



}
