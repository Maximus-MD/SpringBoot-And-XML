package com.cedacri.car_app.repository;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.repositories.impl.CarRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static com.cedacri.car_app.utils.CarUtils.getPreparedCar;
import static com.cedacri.car_app.utils.CarUtils.getPreparedCarList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CarRepositoryTest {

    private static SessionFactory sessionFactory = null;

    private static Session session = null;

    private Transaction transaction;

    private static CarRepository carRepository;

    @BeforeAll
    static void setup(){
        sessionFactory = new Configuration()
                .configure(new File("src/main/resources/META-INF/hibernate-test.cfg.xml"))
                .buildSessionFactory();

        carRepository = new CarRepositoryImpl(sessionFactory);
    }

    @BeforeEach
    void setupThis(){
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    void tearThis(){
        transaction.commit();
    }

    @AfterEach
    void tear(){
        session.close();
    }

    @Test
    void testSave_ReturnCar(){
        Car savedCar = getPreparedCar();

        carRepository.save(savedCar);

        Optional<Car> expected = carRepository.getById(savedCar.getVinCode());

        assertEquals("WDBUF56X48B123654", expected.get().getVinCode());
    }

    @Test
    void testGetAll_ReturnCarList() {
        List<Car> savedCars = getPreparedCarList();

        for(Car car : savedCars){
            carRepository.save(car);
        }

        List<Car> expectedCars = carRepository.getAll();

        assertEquals(savedCars, expectedCars);
    }

    @Test
    void testGetById_ReturnCar() {
        Car car = getPreparedCar();
        carRepository.save(car);

        Optional<Car> retrieved = carRepository.getById(car.getVinCode());

        assertTrue(retrieved.isPresent());
        assertEquals("Mazda", retrieved.get().getName());
    }

    @Test
    void testRemoveById_WhenCarExists() {
        Car car = getPreparedCar();
        carRepository.save(car);

        carRepository.removeById(car.getVinCode());

        Optional<Car> deletedCar = carRepository.getById(car.getVinCode());

        assertTrue(deletedCar.isEmpty());
    }
}
