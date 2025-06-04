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
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OwnerRepositoryTest {

    private final static SessionFactory sessionFactory = new Configuration()
            .configure(new File("src/test/resources/hibernate-test.cfg.xml"))
            .buildSessionFactory();

    private final static OwnerRepository ownerRepository = new OwnerRepositoryImpl(sessionFactory);

    private final static CarRepository carRepository = new CarRepositoryImpl(sessionFactory);

    private Session session = null;

    @BeforeEach
    void setupThis(){
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearThis(){
        session.getTransaction().commit();
    }

    @AfterAll
    static void tear(){
        sessionFactory.close();
    }

    @Test
    @Order(1)
    void testGetAll_ReturnOwnerList() {
        List<Owner> savedOwners = getPreparedOwnersListWithoutCars();

        for (Owner owner : savedOwners) {
            ownerRepository.save(owner);
        }

        List<Owner> expectedOwners = ownerRepository.getAll();

        assertEquals(new HashSet<>(savedOwners), new HashSet<>(expectedOwners));
    }

    @Test
    @Order(2)
    void testSave_UpdateExistentOwner() {
        Owner savedOwner = getPreparedOwnerWithOutCar();
        ownerRepository.save(savedOwner);

        savedOwner.setLastName("Cojocaru");
        ownerRepository.save(savedOwner);

        Optional<Owner> expected = ownerRepository.getById(savedOwner.getUuid());

        assertEquals("Cojocaru", expected.get().getLastName());
    }

    @Test
    @Order(3)
    void whenTryToSaveOwnerWithCar() {
        Car savedCar = getPreparedCar();
        carRepository.save(savedCar);

        Owner savedOwner = getPreparedOwnerWithOutCar();
        savedOwner.setCars(List.of(savedCar));
        savedCar.setOwner(savedOwner);
        ownerRepository.save(savedOwner);

        Optional<Owner> expectedOwner = ownerRepository.getById(savedOwner.getUuid());
        Optional<Car> expectedCar = carRepository.getById(savedCar.getVinCode());

        assertEquals(savedCar.getVinCode(), expectedCar.get().getVinCode());
        assertEquals(savedOwner.getFirstName(), expectedOwner.get().getFirstName());
    }

    @Test
    @Order(4)
    void whenTryToSaveInvalidOwnerData_ThrowOwnerSaveException() {
        Owner savedOwner = getPreparedOwnerWithOutCar();
        savedOwner.setFirstName(null);

        assertThrows(OwnerSaveException.class, () ->
                ownerRepository.save(savedOwner));
    }

    @Test
    @Order(5)
    void testGetById_ReturnOwner() {
        Owner owner = getPreparedOwnerWithOutCar();
        ownerRepository.save(owner);

        Optional<Owner> retrieved = ownerRepository.getById(owner.getUuid());

        assertTrue(retrieved.isPresent(), "Owner should be present but was not found");
        assertEquals("Maria", retrieved.get().getFirstName());
    }

    @Test
    @Order(6)
    void testSave_ReturnOwner() {
        Owner savedOwner = getPreparedOwnerWithOutCar();

        ownerRepository.save(savedOwner);

        Optional<Owner> expected = ownerRepository.getById(savedOwner.getUuid());

        assertEquals(savedOwner.getUuid(), expected.get().getUuid());
    }

    @Test
    @Order(7)
    void testRemoveById() {
        Owner owner = getPreparedOwnerWithOutCar();
        ownerRepository.save(owner);

        ownerRepository.removeById(owner.getUuid());

        Optional<Owner> deletedOwner = ownerRepository.getById(owner.getUuid());

        assertTrue(deletedOwner.isEmpty());
    }
}
