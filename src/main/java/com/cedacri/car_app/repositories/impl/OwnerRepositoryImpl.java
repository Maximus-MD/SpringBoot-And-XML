package com.cedacri.car_app.repositories.impl;

import com.cedacri.car_app.entities.Owner;
import com.cedacri.car_app.repositories.OwnerRepository;
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
public class OwnerRepositoryImpl implements OwnerRepository {
    SessionFactory sessionFactory = new Configuration()
            .configure(new File("src/main/resources/META-INF/hibernate.cfg.xml"))
            .buildSessionFactory();

    Session session = null;

    Transaction transaction = null;

    @Override
    public Optional<Owner> getById(String id) {
        session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Optional<Owner> result = session.createQuery(
                    "SELECT o FROM Owner o LEFT JOIN FETCH o.cars WHERE o.uuid = :uuid", Owner.class)
                    .setParameter("uuid", id)
                    .uniqueResultOptional();

            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Owner> getAll() {
        session = sessionFactory.openSession();
        List<Owner> owners = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            owners = session.createQuery(
                    "SELECT DISTINCT o FROM Owner o LEFT JOIN FETCH o.cars", Owner.class)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return owners;
    }

    @Override
    public Owner save(Owner owner) {
        session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            if (owner.getUuid() == null) {
                session.persist(owner);
            } else {
                session.merge(owner);
            }
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return owner;
    }

    @Override
    public void removeById(String uuid) {
        session = sessionFactory.openSession();

        try {
            transaction = session.beginTransaction();
            Owner owner = session.find(Owner.class, uuid);
            session.remove(owner);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}
