package com.cedacri.car_app.repositories.impl;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.repositories.CarRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CarRepositoryImpl implements CarRepository {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence-unit");

    private EntityManager entityManager;

    @Override
    public Optional<Car> getById(Long id) {
        entityManager = entityManagerFactory.createEntityManager();
        Car car = null;

        try {
            entityManager.getTransaction().begin();
            car = entityManager.find(Car.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAllEntities() {
        entityManager = entityManagerFactory.createEntityManager();
        List<Car> cars = new ArrayList<>();

        try{
            entityManager.getTransaction().begin();
            cars = entityManager.createQuery("FROM Car").getResultList();
            entityManager.getTransaction().commit();
        } catch (Exception e){
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }

        return cars;
    }

    @Override
    public Car save(Car car) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }
}
