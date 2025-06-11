package com.cedacri.car_app.repositories.impl;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.exceptions.CarSaveException;
import com.cedacri.car_app.repositories.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
            log.error("An error occurred while getting car: {}", car, e);
            throw e;
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

            Car existingCar = session.find(Car.class, car.getVinCode());

            if (existingCar == null) {
                session.persist(car);
            } else {
                updateCarFields(existingCar, car);
                session.merge(existingCar);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("An error occurred while saving: {}", car, e);
            throw new CarSaveException("Can't save car to DB!");
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

    private void updateCarFields(Car target, Car source) {
        target.setName(source.getName());
        target.setModel(source.getModel());
        target.setManufactureYear(source.getManufactureYear());
        target.setEnginePower(source.getEnginePower());
        target.setEngineVolume(source.getEngineVolume());
        target.setFuelType(source.getFuelType());
        target.setTransmission(source.getTransmission());
        target.setType(source.getType());
        target.setNumSeats(source.getNumSeats());
        target.setDoorsNum(source.getDoorsNum());
        target.setMaxSpeed(source.getMaxSpeed());
        target.setOwner(source.getOwner());
    }
}
