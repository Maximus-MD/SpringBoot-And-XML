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
        Owner owner = null;

        try {
            transaction = session.beginTransaction();
            owner = session.find(Owner.class, id);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return Optional.ofNullable(owner);
    }

    @Override
    public List<Owner> getAll() {
        session = sessionFactory.openSession();
        List<Owner> owners = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            owners = session.createNativeQuery("SELECT * FROM owners", Owner.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return owners;
    }

    @Override
    public void save(Owner owner) {
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
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
