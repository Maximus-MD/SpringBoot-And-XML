package com.cedacri.car_app.repositories.impl;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.repositories.CarRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CarRepositoryImpl implements CarRepository {

    private final SessionFactory sessionFactory;

    public CarRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Car> getById(String id) {
        Transaction transaction = null;

        Car car = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            car = session.find(Car.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        Transaction transaction = null;
        List<Car> cars = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            cars = session.createNativeQuery("SELECT * FROM cars", Car.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return cars;
    }

    @Override
    public Car save(Car car) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return car;
    }

    @Override
    public void removeById(String id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Car car = session.find(Car.class, id);
            session.remove(car);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
