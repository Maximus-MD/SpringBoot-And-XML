package com.cedacri.car_app.repositories;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<T, ID> {
    Optional<T> getById(ID id);

    List<T> getAll();

    T save(T t);

    void removeById(ID id);
}