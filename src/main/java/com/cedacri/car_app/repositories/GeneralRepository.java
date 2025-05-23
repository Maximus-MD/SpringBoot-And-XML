package com.cedacri.car_app.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneralRepository<T, ID> {
    Optional<T> getById(ID id);

    List<T> getAllEntities();

    T save(T t);

    void removeById(ID id);
}