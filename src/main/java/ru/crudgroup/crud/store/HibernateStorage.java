package ru.crudgroup.crud.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import ru.crudgroup.crud.models.User;

import java.util.Collection;

public class HibernateStorage implements Storage {

    private static HibernateStorage instance = null;
    private HibernateStorage() {}
    public static HibernateStorage getInstance() {
        if (instance == null) instance = new HibernateStorage();
        return instance;
    }

    private final SessionFactory factory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public Collection<User> values() {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return session.createQuery("from User").list();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public Collection<User> values(String sort, int offset, int row_count) {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return session.createQuery(String.format("from User order by %s", sort)).setFirstResult(offset).setMaxResults(row_count).list();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public int add(final User user) {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(user);
            return user.getId();
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void edit(User user) {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(user);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(get(id));
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public User get(int id) {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return (User) session.get(User.class, id);
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public int generateId() {
        final Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            return Integer.parseInt(session.createQuery("select id from User order by id desc").setFirstResult(0).list().get(0).toString()) + 1;
        } finally {
            tx.commit();
            session.close();
        }
    }

    @Override
    public void close() {
        this.factory.close();
    }
}
