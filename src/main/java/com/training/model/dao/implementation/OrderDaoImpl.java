package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.OrderDao;
import com.training.model.entity.Order;
import com.training.model.exeptions.DBExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static final Logger LOGGER = LogManager.getLogger(OrderDaoImpl.class);

    @Override
    public int create(Order entity, Connection connection) {
        String sql = "INSERT into orders (user_id, service_id, status, manager_id, review_date, " +
                "rejection_reason, master_id, repair_start_time, repair_finish) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getServiceId());
            preparedStatement.setString(3, entity.getStatus());
            preparedStatement.setInt(4,entity.getManagerId());
            preparedStatement.setTimestamp(5,Timestamp.valueOf(entity.getReviewDate()));
            preparedStatement.setString(6,entity.getRejectionReason());
            preparedStatement.setInt(7, entity.getMasterId());
            preparedStatement.setTimestamp(8,Timestamp.valueOf(entity.getRepairStartTime()));
            preparedStatement.setTimestamp(9,Timestamp.valueOf(entity.getRepairFinish()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create order in database", e.getCause());
            throw new DBExeption("message.error.order");
        }

        return id;
    }

    @Override
    public Order read(Integer id, Connection connection) {
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
                        setReviewDate(resultSet.getTimestamp("review_date").toLocalDateTime()).
                        setRejectionReason(resultSet.getString("rejection_reason")).
                        setMasterId(resultSet.getInt("master_id")).
                        setRepairStartTime(resultSet.getTimestamp("repair_start_time").toLocalDateTime()).
                        setRepairFinish(resultSet.getTimestamp("repair_finish").toLocalDateTime()).
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read order from database", e.getCause());
            throw  new DBExeption("message.error.order");
        }
        return order;
    }

    @Override
    public void update(Order entity, Connection connection) {
        String sql = "UPDATE orders SET user_id=?, service_id=?, status=?, manager_id=?, review_date=?," +
                "rejection_reason=?, master_id=?, repair_start_time=?, repair_finish=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, entity.getUserId());
            preparedStatement.setInt(2, entity.getServiceId());
            preparedStatement.setString(3,entity.getStatus());
            preparedStatement.setInt(4, entity.getManagerId());
            preparedStatement.setTimestamp(5,Timestamp.valueOf(entity.getReviewDate()));
            preparedStatement.setString(6,entity.getRejectionReason());
            preparedStatement.setInt(7,entity.getMasterId());
            preparedStatement.setTimestamp(8, Timestamp.valueOf(entity.getRepairStartTime()));
            preparedStatement.setTimestamp(9, Timestamp.valueOf(entity.getRepairFinish()));
            preparedStatement.setInt(10,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update order in database",e.getCause());
            throw new DBExeption("message.error.order");
        }
    }

    @Override
    public void delete(Order entity, Connection connection) {
        String sql = "DELETE FROM orders WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to delete order from database",e.getCause());
            throw new DBExeption("message.error.order");
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
                        setReviewDate(resultSet.getTimestamp("review_date").toLocalDateTime()).
                        setRejectionReason(resultSet.getString("rejection_reason")).
                        setMasterId(resultSet.getInt("master_id")).
                        setRepairStartTime(resultSet.getTimestamp("repair_start_time").toLocalDateTime()).
                        setRepairFinish(resultSet.getTimestamp("repair_finish").toLocalDateTime()).
                        build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read orders from database",e.getCause());
            throw new DBExeption("message.error.order");
        }
        return list;
    }

    private void setIntOrNull(int index, Integer integer, PreparedStatement preparedStatement) throws SQLException{
        if (integer == null){
            preparedStatement.setNull(index, Types.INTEGER);
        } else {
            preparedStatement.setInt(index, integer);
        }
    }

    private void setDateOrNull(int index, LocalDateTime date, PreparedStatement preparedStatement) throws SQLException{
        if(date == null){
            preparedStatement.setNull(index, Types.DATE);
        } else {
            preparedStatement.setTimestamp(index, Timestamp.valueOf(date));
        }
    }
}
