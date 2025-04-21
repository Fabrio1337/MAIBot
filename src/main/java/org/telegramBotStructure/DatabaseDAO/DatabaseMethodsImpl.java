package org.telegramBotStructure.DatabaseDAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.telegramBotStructure.entity.*;

@Repository
public class DatabaseMethodsImpl implements DatabaseMethods{

    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public Admin getAdmin(long chatId) {
        return null;
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
    public void setAdmin(Admin admin) {

    }

    @Override
    public void setSubject(Subject subject) {

    }

    @Override
    public void setUser(User user) {

    }

    @Override
    public void setGroup(MaiGroup maiGroup) {

    }

    @Override
    public void setMailing(Mailing mailing) {

    }

    @Override
    public void setHomework(Homework homework) {

    }

    @Override
    public void setSchedule(Schedule schedule) {

    }
}
