package dev.hayann.repository;

import java.util.List;

public interface Repository<T> {

    public T findById(int id);

    public List<T> findAll();

    public void persist(T t);

    public void update(T t);
}