package com.training.model.dao.interfaces;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<E, K> {

    int create(E entity, Connection connection);

    E read(K id, Connection connection);

    void update(E entity, Connection connection);

    void delete(E entity, Connection connection);

    List<E> getAll(Connection connection);

}
