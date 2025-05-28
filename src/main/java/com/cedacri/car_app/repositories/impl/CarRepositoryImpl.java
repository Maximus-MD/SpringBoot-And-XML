package com.cedacri.car_app.repositories.impl;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    SessionFactory sessionFactory = new Configuration()
            .configure(new File("src/main/resources/META-INF/hibernate.cfg.xml"))
            .buildSessionFactory();

    Session session = null;

    Transaction transaction = null;

    @Override
    public Optional<Car> getById(String id) {
        session = sessionFactory.openSession();
        Car car = null;

        try {
            transaction = session.beginTransaction();
            car = session.find(Car.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        session = sessionFactory.openSession();
        List<Car> cars = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            cars = session.createNativeQuery("SELECT * FROM cars", Car.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return cars;
    }

    @Override
    public Car save(Car car) {
        session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            if (car.getVinCode() == null) {
                session.persist(car);
            } else {
                session.merge(car);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return car;
    }

    @Override
    public void removeById(String id) {
        session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Car car = session.find(Car.class, id);
            session.remove(car);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
