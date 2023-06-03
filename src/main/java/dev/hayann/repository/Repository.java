package dev.hayann.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    public List<T> findAll();

    public void persist(T t) throws SQLException;

    public void update(T t) throws SQLException;
}
