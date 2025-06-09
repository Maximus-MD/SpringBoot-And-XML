package com.cedacri.car_app.repository;

import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.Owner;
import com.cedacri.car_app.exceptions.OwnerSaveException;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.repositories.OwnerRepository;
import com.cedacri.car_app.repositories.impl.CarRepositoryImpl;
import com.cedacri.car_app.repositories.impl.OwnerRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.cedacri.car_app.utils.CarUtils.getPreparedCar;
import static com.cedacri.car_app.utils.OwnerUtils.getPreparedOwnerWithOutCar;
import static com.cedacri.car_app.utils.OwnerUtils.getPreparedOwnersListWithoutCars;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OwnerRepositoryTest {

    private final static SessionFactory sessionFactory = new Configuration()
            .configure(new File("src/test/resources/hibernate-test.cfg.xml"))
            .buildSessionFactory();

    private final static OwnerRepository ownerRepository = new OwnerRepositoryImpl(sessionFactory);

    private final static CarRepository carRepository = new CarRepositoryImpl(sessionFactory);

    @AfterEach
    void cleanDatabase() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM Car").executeUpdate();
            session.createMutationQuery("DELETE FROM Owner").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    void testGetAll_ReturnOwnerList() {
        List<Owner> savedOwners = getPreparedOwnersListWithoutCars();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Owner owner : savedOwners) {
                ownerRepository.save(owner);
            }
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Owner> expectedOwners = ownerRepository.getAll();

            assertEquals(new HashSet<>(savedOwners), new HashSet<>(expectedOwners));
            session.getTransaction().commit();
        }

    }

    @Test
    void testSave_UpdateExistentOwner() {
        Owner savedOwner = getPreparedOwnerWithOutCar();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.save(savedOwner);
            session.getTransaction().commit();
        }

        savedOwner.setLastName("Cojocaru");

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.save(savedOwner);
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Owner> expected = ownerRepository.getById(savedOwner.getUuid());
            assertEquals("Cojocaru", expected.get().getLastName());
            session.getTransaction().commit();
        }
    }

    @Test
    void whenTryToSaveOwnerWithCar() {
        Car savedCar = getPreparedCar();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            carRepository.save(savedCar);
            session.getTransaction().commit();
        }

        Owner savedOwner = getPreparedOwnerWithOutCar();
        savedOwner.setCars(List.of(savedCar));
        savedCar.setOwner(savedOwner);

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.save(savedOwner);
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Owner> expectedOwner = ownerRepository.getById(savedOwner.getUuid());
            Optional<Car> expectedCar = carRepository.getById(savedCar.getVinCode());

            assertEquals(savedCar.getVinCode(), expectedCar.get().getVinCode());
            assertEquals(savedOwner.getFirstName(), expectedOwner.get().getFirstName());
            session.getTransaction().commit();
        }
    }

    @Test
    void whenTryToSaveInvalidOwnerData_ThrowOwnerSaveException() {
        Owner savedOwner = getPreparedOwnerWithOutCar();
        savedOwner.setFirstName(null);

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            assertThrows(OwnerSaveException.class, () ->
                    ownerRepository.save(savedOwner));

            session.getTransaction().commit();
        }
    }

    @Test
    void testGetById_ReturnOwner() {
        Owner savedOwner = getPreparedOwnerWithOutCar();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.save(savedOwner);
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Owner> retrieved = ownerRepository.getById(savedOwner.getUuid());
            assertTrue(retrieved.isPresent(), "Owner should be present but was not found");
            assertEquals("Maria", retrieved.get().getFirstName());

            session.getTransaction().commit();
        }
    }

    @Test
    void testSave_ReturnOwner() {
        Owner savedOwner = getPreparedOwnerWithOutCar();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.save(savedOwner);
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Owner> expected = ownerRepository.getById(savedOwner.getUuid());
            assertEquals(savedOwner.getUuid(), expected.get().getUuid());
            session.getTransaction().commit();
        }
    }

    @Test
    void testRemoveById() {
        Owner savedOwner = getPreparedOwnerWithOutCar();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.save(savedOwner);
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            ownerRepository.removeById(savedOwner.getUuid());
            session.getTransaction().commit();
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Optional<Owner> deletedOwner = ownerRepository.getById(savedOwner.getUuid());
            assertTrue(deletedOwner.isEmpty());

            session.getTransaction().commit();
        }
    }
}