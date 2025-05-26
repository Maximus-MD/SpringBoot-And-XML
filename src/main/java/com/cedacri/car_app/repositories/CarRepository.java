package com.cedacri.car_app.repositories;

import com.cedacri.car_app.entities.Car;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends GeneralRepository<Car, String> {
}

