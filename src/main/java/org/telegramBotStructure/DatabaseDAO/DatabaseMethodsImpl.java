package org.telegramBotStructure.DatabaseDAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.telegramBotStructure.entity.*;

@Repository
public class DatabaseMethodsImpl implements DatabaseMethods{

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    @Transactional
    public Admin getAdmin(long chatId) {
        Session session = sessionFactory.getCurrentSession();
        Admin admin = session.createQuery("from Admin admin where userId= :chatId", Admin.class)
                .setParameter("chatId", chatId)
                .uniqueResult();
        if(admin == null)
        {
            return null;
        }
        else {
            return admin;
        }
    }

    @Override
    public Subject getSubject(String subjectName) {
        return null;
    }

    @Override
    public User getUser(long chatId) {
        return null;
    }

    @Override
    public MaiGroup getGroup(long chatId) {
        return null;
    }

    @Override
    public Mailing getMailing(String group) {
        return null;
    }

    @Override
    public Weekday getWeekday(String day) {
        return null;
    }

    @Override
    public Homework getHomework(String homework) {
        return null;
    }

    @Override
    public Schedule getSchedule(long id) {
        return null;
    }

    @Override
    @Transactional
    public void setAdmin(Admin admin) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(admin);
    }

    @Override
    @Transactional
    public void setSubject(Subject subject) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(subject);
    }

    @Override
    @Transactional
    public void setUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    @Override
    @Transactional
    public void setGroup(MaiGroup maiGroup) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(maiGroup);
    }

    @Override
    @Transactional
    public void setMailing(Mailing mailing) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(mailing);
    }

    @Override
    @Transactional
    public void setHomework(Homework homework) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(homework);
    }

    @Override
    @Transactional
    public void setSchedule(Schedule schedule) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(schedule);
    }
}
