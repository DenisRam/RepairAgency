package com.training.web.services;

import com.training.model.dao.DaoFactory;
import com.training.model.dao.interfaces.OrderDao;
import com.training.model.entity.Order;
import com.training.model.exeptions.DataBaseException;
import com.training.model.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    OrderDao orderDao = DaoFactory.getOrderDao();

    /**
     * Get all user's orders with service name
     * @param userId user's id in database
     * @return Map with key services name and value user's orders
     * @exception DataBaseException error in Dao layer
     */
    public Map<String, Order> getUsersOrdersByUserId(int userId){
        Connection connection = ConnectionPool.getConnection(true);
        Map<String, Order> map = null;
        try {
            map = orderDao.getOrdersByUserId(userId, connection);
        }catch (DataBaseException e){
            LOGGER.error("Can't get users's orders", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return map;
    }

    /**
     * Create new order byuser
     * @param userId user's id
     * @param serviceId services id
     * @param status status NEW
     * @return
     */
    public int createNewOrder(int userId,int serviceId, String status){
        Connection connection = ConnectionPool.getConnection(false);
        Order order = new Order.Builder()
                .setUserId(userId)
                .setServiceId(serviceId)
                .setStatus(status)
                .build();
        int orderId;
        try {
            orderId = orderDao.create(order, connection);
            ConnectionPool.commitTransaction(connection);
        }catch (DataBaseException e){
            LOGGER.error("Can'tc reate order", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return orderId;
    }

    public void confirmOrder(int orderId, int managerId, String status, String rejectionReason){
        Connection connection = ConnectionPool.getConnection(false);
        try {
            orderDao.updateOrderToRefuseOrComfir(orderId, managerId,  status, new Date(new java.util.Date().getTime()),rejectionReason,connection);
            ConnectionPool.commitTransaction(connection);
        } catch (DataBaseException e){
        LOGGER.error("Can't update order(to confirm)", e.getCause());
        throw e;
    }finally {
        ConnectionPool.closeConnection(connection);
    }
    }

    public void startRepair(int orderId, int masterId){
        Connection connection = ConnectionPool.getConnection(false);
        try {
            orderDao.updateOrderToRepaiStart(orderId, masterId, "IN_WORK", new Date(new java.util.Date().getTime()), connection);
            ConnectionPool.commitTransaction(connection);
        } catch (DataBaseException e){
            LOGGER.error("Can't update order(to in work)", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
    }


    public void finishRepair(int orderId){
        Connection connection = ConnectionPool.getConnection(false);
        try {
            orderDao.updateOrderToRepairFinish(orderId, "DONE", new Date(new java.util.Date().getTime()), connection);
            ConnectionPool.commitTransaction(connection);
        } catch (DataBaseException e){
            LOGGER.error("Can't update order(to in done)", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
    }

    public List<Order> getOrdersByUserIdAndRole(Integer userId, String role){
        Connection connection = ConnectionPool.getConnection(true);
        List<Order> list = null;
            try {
                list = orderDao.getAll(connection);
                if(role.equals("MANAGER")){
                    list = orderDao.getAll(connection).stream()
                            .filter(order -> order.getStatus().contains("NEW"))
                            .collect(Collectors.toList());
                } else {
                    list = orderDao.getAll(connection).stream()
                            .filter(order -> order.getStatus().contains("CONFIRM") | order.getMasterId().equals(userId) & !order.getStatus().contains("DONE"))
                            .collect(Collectors.toList());
//                    List<Order> allorders = orderDao.getAll(connection);
//
//
//                    for (Order o: allorders) {
//                        if (o.getMasterId().equals(userId) | o.getStatus().equals("CONFIRM")){
//                            list.add(o);
//                        }
//                    }
                }
            }catch (DataBaseException e){
            LOGGER.error("Can't get users's orders", e.getCause());
            throw e;
        }finally {
            ConnectionPool.closeConnection(connection);
        }
        return list;
    }

}
