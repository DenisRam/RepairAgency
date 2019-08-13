package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.OrderDao;
import com.training.model.entity.Order;
import com.training.model.exeptions.DataBaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);


    @Override
    public int create(int userId, int serviceId, String status, int managerId, Date reviewDate, String rejectionReason,
                      int masterId, Date repairStartTime, Date repairFinish, Connection connection) {
        String sql = "INSERT into orders (user_id, service_id, status, manager_id, review_date, " +
                "rejection_reason, master_id, repair_start_time, repair_finish) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, serviceId);
            preparedStatement.setString(3, status);
            preparedStatement.setInt(4,managerId);
            preparedStatement.setDate(5,reviewDate);
            preparedStatement.setString(6,rejectionReason);
            preparedStatement.setInt(7, masterId);
            preparedStatement.setDate(8,repairStartTime);
            preparedStatement.setDate(9,repairFinish);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create order in database", e.getCause());
            throw new DataBaseException("message.error.order");
        }

        return id;
    }

    @Override
    public int create(Order order, Connection connection) throws DataBaseException {
        String sql = "INSERT into orders (user_id, service_id, status, manager_id, review_date, " +
                "rejection_reason, master_id, repair_start_time, repair_finish) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getServiceId());
            prepareStatementSetStringOrNull(3,order.getStatus(),preparedStatement);
            prepareStatementSetIntOrNull(4,order.getManagerId(),preparedStatement);
            prepareStatementSetDateOrNull(5,order.getReviewDate(), preparedStatement);
            prepareStatementSetStringOrNull(6,order.getRejectionReason(),preparedStatement);
            prepareStatementSetIntOrNull(7, order.getMasterId(),preparedStatement);
            prepareStatementSetDateOrNull(8,order.getRepairStartTime(), preparedStatement);
            prepareStatementSetDateOrNull(9,order.getRepairFinish(), preparedStatement);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create order in database", e.getCause());
            throw new DataBaseException("message.error.order");
        }

        return id;
    }

    @Override
    public Order read(int id, Connection connection) {
        Order order = new Order();
        String sql = "SELECT * FROM orders WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                order = new Order.Builder().
                        setId(resultSet.getInt("id")).
                        setUserId(resultSet.getInt("user_id")).
                        setServiceId(resultSet.getInt("service_id")).
                        setStatus(resultSet.getString("status")).
                        setManagerId(resultSet.getInt("manager_id")).
                        setReviewDate(resultSet.getDate("review_date")).
                        setRejectionReason(resultSet.getString("rejection_reason")).
                        setMasterId(resultSet.getInt("master_id")).
                        setRepairStartTime(resultSet.getDate("repair_start_time")).
                        setRepairFinish(resultSet.getDate("repair_finish")).
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read order from database", e.getCause());
            throw  new DataBaseException("message.error.order");
        }
        return order;
    }

    @Override
    public void update(Order order, Connection connection) {
        String sql = "UPDATE orders SET user_id=?, service_id=?, status=?, manager_id=?, review_date=?," +
                "rejection_reason=?, master_id=?, repair_start_time=?, repair_finish=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getServiceId());
            preparedStatement.setString(3,order.getStatus());
            preparedStatement.setInt(4, order.getManagerId());
            preparedStatement.setDate(5,order.getReviewDate());
            preparedStatement.setString(6,order.getRejectionReason());
            preparedStatement.setInt(7,order.getMasterId());
            preparedStatement.setDate(8, order.getRepairStartTime());
            preparedStatement.setDate(9, order.getRepairFinish());;
            preparedStatement.setInt(10,order.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update order in database",e.getCause());
            throw new DataBaseException("message.error.order");
        }
    }


    @Override
    public void delete(Order order, Connection connection) {
        String sql = "DELETE FROM orders WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,order.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to delete order from database",e.getCause());
            throw new DataBaseException("message.error.order");
        }
    }

    @Override
    public List<Order> getAll(Connection connection) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY id";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                list.add(new Order.Builder().
                        setId(resultSet.getInt("id")).
                        setUserId(resultSet.getInt("user_id")).
                        setServiceId(resultSet.getInt("service_id")).
                        setStatus(resultSet.getString("status")).
                        setManagerId(resultSet.getInt("manager_id")).
                        setReviewDate(resultSet.getDate("review_date")).
                        setRejectionReason(resultSet.getString("rejection_reason")).
                        setMasterId(resultSet.getInt("master_id")).
                        setRepairStartTime(resultSet.getDate("repair_start_time")).
                        setRepairFinish(resultSet.getDate("repair_finish")).
                        build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read orders from database",e.getCause());
            throw new DataBaseException("message.error.order");
        }
        return list;
    }

    @Override
    public void updateOrderToRefuseOrComfir(int id, int managerId, String status, Date reviewDate, String rejectionReason, Connection connection) throws DataBaseException {

        String sql = "UPDATE orders SET status=?, manager_id=?, review_date=?," +
                "rejection_reason=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, managerId);
            preparedStatement.setDate(3, reviewDate);
            prepareStatementSetStringOrNull(4, rejectionReason, preparedStatement);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update order in database",e.getCause());
            throw new DataBaseException("message.error.order");
        }
    }

    @Override
    public void updateOrderToRepaiStart(int id, int masterId, String status, Date repairStart, Connection connection) throws DataBaseException {
        String sql = "UPDATE orders SET master_id=?, status=?,  repair_start_time=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, masterId);
            preparedStatement.setString(2, status);
            preparedStatement.setDate(3, repairStart);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update order in database",e.getCause());
            throw new DataBaseException("message.error.order");
        }
    }

    @Override
    public void updateOrderToRepairFinish(int id, String status, Date repairFinish, Connection connection) throws DataBaseException {

        String sql = "UPDATE orders SET status=?, repair_finish=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, repairFinish);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update order in database",e.getCause());
            throw new DataBaseException("message.error.order");
        }
    }


    @Override
    public Map<String, Order> getOrdersByUserId(int userId, Connection connection) throws DataBaseException {
        Map<String, Order> map = new LinkedHashMap<>();
        String sql = "SELECT * FROM  orders INNER JOIN services ON orders.service_id = services.id WHERE user_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                map.put(resultSet.getString("service_name"),new Order.Builder().
                        setId(resultSet.getInt("id")).
                        setUserId(resultSet.getInt("user_id")).
                        setServiceId(resultSet.getInt("service_id")).
                        setStatus(resultSet.getString("status")).
                        setManagerId(resultSet.getInt("manager_id")).
                        setReviewDate(resultSet.getDate("review_date")).
                        setRejectionReason(resultSet.getString("rejection_reason")).
                        setMasterId(resultSet.getInt("master_id")).
                        setRepairStartTime(resultSet.getDate("repair_start_time")).
                        setRepairFinish(resultSet.getDate("repair_finish")).
                        build());
            }
        } catch (SQLException e){
            LOGGER.error("Unable to read comments selected by user id from database",e.getCause());
            throw new DataBaseException("message.error.comment");
        }
        return map;
    }

    private void prepareStatementSetIntOrNull(int index, Integer integer, PreparedStatement preparedStatement) throws SQLException {
        if (integer == null) {
            preparedStatement.setNull(index, Types.INTEGER);
        } else {
            preparedStatement.setInt(index, integer);
        }
    }

    private void prepareStatementSetStringOrNull(int index, String string, PreparedStatement preparedStatement) throws SQLException {
        if (string == null) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            preparedStatement.setString(index, string);
        }
    }
    private void prepareStatementSetDateOrNull(int index, Date date, PreparedStatement preparedStatement) throws SQLException {
        if (date == null) {
            preparedStatement.setNull(index, Types.DATE);
        } else {
            preparedStatement.setDate(index, date);
        }
    }
}
