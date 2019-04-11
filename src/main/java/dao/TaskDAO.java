package dao;

import database.HibernateFactory;
import entity.Task;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    public void add(Task task) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void setIsCompleted(Long id) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Task task = session.find(Task.class, id);
            task.setCompleted(true);
            session.getTransaction().commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Task> readAll(Long userId) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        List<Task> taskList = new ArrayList<Task>();
        try {
            Query query = session.createQuery(String.format("FROM %s WHERE userId=%d AND isCompleted=%d ORDER BY date ASC", Task.class.getSimpleName(), userId, 0));
            taskList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return taskList;
    }
}
