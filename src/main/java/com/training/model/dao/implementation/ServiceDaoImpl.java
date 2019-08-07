package com.training.model.dao.implementation;

import com.training.model.dao.interfaces.ServiceDao;
import com.training.model.entity.Service;
import com.training.model.exeptions.DBExeption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDaoImpl implements ServiceDao {

    private static final Logger LOGGER = LogManager.getLogger(ServiceDaoImpl.class);

    @Override
    public int create(Service entity, Connection connection) {
        String sql = "INSERT into services (service_name_ua, service_name_en, price) VALUES (?, ?, ?)";
        int id = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getServiceNameUa());
            preparedStatement.setString(2, entity.getServiceNameEn());
            preparedStatement.setBigDecimal(3, entity.getPrice());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Unable to create service in database", e.getCause());
            throw new DBExeption("message.error.service");
        }
        return id;
    }

    @Override
    public Service read(Integer id, Connection connection) {
        Service service = new Service();
        String sql = "SELECT * FROM services WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                service = new Service.Builder().
                        setServiceNameUa(resultSet.getString("service_name_ua")).
                        setServiceNameEn(resultSet.getString("service_name_en")).
                        setPrice(resultSet.getBigDecimal("price")).
                        build();
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read service from database", e.getCause());
            throw  new DBExeption("message.error.service");
        }
        return service;
    }

    @Override
    public void update(Service entity, Connection connection) {
        String sql = "UPDATE services SET service_name_ua=?, service_name_en=?, price=? WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, entity.getServiceNameUa());
            preparedStatement.setString(2, entity.getServiceNameEn());
            preparedStatement.setBigDecimal(3, entity.getPrice());
            preparedStatement.setInt(4, entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to update service in database",e.getCause());
            throw new DBExeption("message.error.service");
        }
    }

    @Override
    public void delete(Service entity, Connection connection) {
        String sql = "DELETE FROM services WHERE id=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Unable to delete service from database",e.getCause());
            throw new DBExeption("message.error.service");
        }
    }

    @Override
    public List<Service> getAll(Connection connection) {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services ORDER BY id";
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                list.add(new Service.Builder().
                        setId(resultSet.getInt("id")).
                        setServiceNameUa(resultSet.getString("service_name_ua")).
                        setServiceNameEn(resultSet.getString("service_name_en")).
                        setPrice(resultSet.getBigDecimal("price")).
                        build());
            }
        }catch (SQLException e){
            LOGGER.error("Unable to read services from database",e.getCause());
            throw new DBExeption("message.error.service");
        }
        return list;
    }
}
