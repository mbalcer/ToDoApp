package dao;

import database.HibernateFactory;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserDAO {
    public void add(User user) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Optional<User> read(String login) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();

        try {
            Query query = session.createQuery("FROM User WHERE login='"+login+"'");
            User user = (User) query.list().get(0);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }
}
