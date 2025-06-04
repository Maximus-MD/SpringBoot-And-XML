package com.cedacri.car_app.repositories.impl;

import com.cedacri.car_app.entities.Owner;
import com.cedacri.car_app.exceptions.OwnerSaveException;
import com.cedacri.car_app.repositories.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OwnerRepositoryImpl implements OwnerRepository {

    private final SessionFactory sessionFactory;

    public OwnerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Owner> getById(String id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Optional<Owner> result = session.createQuery(
                            "SELECT o FROM Owner o LEFT JOIN FETCH o.cars WHERE o.uuid = :uuid", Owner.class)
                    .setParameter("uuid", id)
                    .uniqueResultOptional();

            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Owner> getAll() {
        List<Owner> owners = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            owners = session.createQuery(
                            "SELECT DISTINCT o FROM Owner o LEFT JOIN FETCH o.cars", Owner.class)
                    .getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return owners;
    }

    @Override
    public Owner save(Owner owner) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (owner.getUuid() == null) {
                session.persist(owner);
            } else {
                session.merge(owner);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("An error occurred while saving: {}", owner, e);
            throw new OwnerSaveException("Can't save owner to DB!");
        }
        return owner;
    }

    @Override
    public void removeById(String uuid) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Owner owner = session.find(Owner.class, uuid);
            session.remove(owner);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
