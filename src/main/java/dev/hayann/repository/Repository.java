package dev.hayann.repository;

import java.util.List;

public interface Repository<T> {

    public List<T> findById(String id);
}
