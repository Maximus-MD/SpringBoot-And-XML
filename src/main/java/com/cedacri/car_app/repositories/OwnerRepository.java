package com.cedacri.car_app.repositories;

import com.cedacri.car_app.entities.Owner;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends GeneralRepository<Owner, String> {
}
