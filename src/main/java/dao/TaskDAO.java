package dao;

import database.HibernateFactory;
import entity.Task;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> readAll(Long userId) {
        HibernateFactory hibernateFactory = new HibernateFactory();
        Session session = hibernateFactory.getSessionFactory().openSession();
        List<Task> taskList = new ArrayList<Task>();
        try {
            Query query = session.createQuery(String.format("FROM %s WHERE userId=%d", Task.class.getSimpleName(), userId));
            taskList = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return taskList;
    }
}