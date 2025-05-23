package com.cedacri.car_app.repositories;

import com.cedacri.car_app.entities.CarPassport;
import org.springframework.stereotype.Repository;

@Repository
public interface CarPassportRepository extends GeneralRepository<CarPassport, Long> {
}