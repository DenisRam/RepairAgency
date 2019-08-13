package com.training.web.services;

import com.training.model.dao.DaoFactory;
import com.training.model.dao.interfaces.ServiceDao;
import com.training.model.entity.Service;
import com.training.model.exeptions.DataBaseException;
import com.training.model.util.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;

public class ServiceManagementService {

    private static final Logger LOGGER = LogManager.getLogger(ServiceManagementService.class);

    ServiceDao serviceDao = DaoFactory.getServiceDao();

    /**
     * Get all services from database
     * @return List with all services if the search is successful or null if not
     * @exception DataBaseException error in Dao layer
     */
    public List<Service> getAllServices(){
        Connection connection = ConnectionPool.getConnection(true);
        List<Service> services = null;
        try {
            services = serviceDao.getAll(connection);
        }catch (DataBaseException e){
            LOGGER.error("Cannot get all services", e.getCause());
            throw e;
        } finally {
            ConnectionPool.closeConnection(connection);
        }
        return services;
    }
}
